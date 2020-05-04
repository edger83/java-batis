package com.lagou.sqlSession.sqlnode.condition;

import com.lagou.sqlSession.meta.MapWrapper;
import com.lagou.sqlSession.meta.MetaObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 2020-03-27 条件选择。
 * new ConditionGroup("name==10 || xx == null").test(obj) <br/>
 * new ConditionGroup("name==10 || (xx == 1 && dd == 3)").test(obj) <br/>
 *
 */
public class ConditionGroup implements Condition{
    private List<Object> context = new ArrayList<>();
    // 符号字典
    private static Symbols compare = Symbols.create("==" , "!=" , ">=" , "<=" , ">" , "<" , "&&" , "||");
//    private static Symbols logical = Symbols.create("&&" , "||");
    private int endIndex;
    private int off;
    boolean child = false;
    public ConditionGroup(String testText, int off) {
        this.off = off;
        int start = Symbols.indexLeftChar('(', testText, off);
        if (start == -1) {
            start = off;
        } else {
            start = start + 1;
            child = true;
        }

        while (start < testText.length()) {

            if (child) {
                int leftEndIndex = Symbols.indexLeftChar(')', testText, start);
                if (leftEndIndex != -1) {
                    this.endIndex = leftEndIndex;
                    break;
                }
            }

            int leftCreatrIndex = Symbols.indexLeftChar('(', testText, start);
            if(leftCreatrIndex != -1) { //  左侧文本是 (
                ConditionGroup conditionGroup = new ConditionGroup(testText, leftCreatrIndex);
                start = conditionGroup.endIndex + 1;
                this.context.add(conditionGroup);
                continue;
            }
            SymbolText symbolText = compare.searchOne(testText, start);
            if (symbolText != null) {
                if (symbolText.is("&&", "||")) { //
                    ConditionItem.LogicalOperator logicalOperator = new ConditionItem.LogicalOperator(symbolText.getText());
                    start = symbolText.getEndIndex() + 1;
                    this.context.add(logicalOperator);
                } else { //
                    int textEndIndex = symbolText.getStartIndex() - 1; // 文本结束
                    if (textEndIndex < start) {
                        throw new RuntimeException("符号前无字段名" + symbolText.getText());
                    }
                    String leftText = getLeftText(testText, start, textEndIndex); // 左侧文本
                    start = symbolText.getEndIndex() + 1;


                    textEndIndex = testText.length() - 1;
                    if (textEndIndex < start) {
                        throw new RuntimeException("符号后未设置数据" + symbolText.getText());
                    }

                    Text rightText = getRightTest(testText, start, textEndIndex); // 右侧值
                    ConditionItem conditionItem = new ConditionItem(leftText, symbolText.getText(), rightText.text);
                    this.context.add(conditionItem);
                    start = rightText.endIndex + 1;
                }
            } else {
                break;
            }
        }

        if (this.endIndex == -1 && this.child) {
            throw new RuntimeException("未找到结束标记" );
        }
    }
    private Text getRightTest(String text, int startIndex, int endIndex) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean empty = true;
        boolean flag = false;
        int i = startIndex;
        for (; i <= endIndex; i ++ ) {
            char c = text.charAt(i);
            if (!flag && c == '\'') {
                flag = true;
            }
            if (c == ' ') {
                if (flag) {
                    stringBuilder.append(c);
                } else if (!empty) {
                    break;
                }
            } else if (isCharOk(c) || c == '\'') {
                empty = false;
                stringBuilder.append(c);
            } else {
                if (child && c == ')') {
                    break;
                } else {
                    throw new RuntimeException("错误对字符 " + c);
                }
            }
        }
        String result = null;
        if (stringBuilder.length() == 0) {
            throw new RuntimeException("值设置不可是空的！");
        }
        int s1 = stringBuilder.charAt(0);
        int s2 = stringBuilder.charAt(stringBuilder.length() - 1);

        if (s1 == '\'') {
            if (stringBuilder.length() == 1 || s1 != '\'') {
                throw new RuntimeException("未找到结束符号");
            }
        } else if (s2 == '\'') {
            throw new RuntimeException("未找到开始符号");
        }


        result = stringBuilder.toString(); // 这是一个字符串
        return new Text(i - 1, result);
    }
    private String getLeftText(String text, int startIndex, int endIndex) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean empty = true;

        for (int i = startIndex; i <= endIndex; i ++ ) {
            char c = text.charAt(i);
            if (c == ' ') {
                if (!empty) {
                    break;
                }
            } else if (isCharOk(c)) {
                empty = false;
                stringBuilder.append(c);
            } else {
                throw  new RuntimeException("错误对字符 " + c);
            }

        }
        if (stringBuilder.length() == 0) {
            throw new RuntimeException("值设置不可是空的！");
        }
        return stringBuilder.toString();
    }

    public boolean test(MetaObject metaObject) {
        boolean testOk = false;
        ConditionItem.LogicalOperator logicalOperator = null;
        for (Object o : context) {
            if (o instanceof  Condition) {
                Condition condition = (Condition) o;
                if (logicalOperator != null) {
                    testOk = logicalOperator.test(testOk, condition, metaObject);
                    logicalOperator = null;
                } else {

                    testOk = condition.test(metaObject);
                }
            } else if (o instanceof ConditionItem.LogicalOperator) {
                logicalOperator = (ConditionItem.LogicalOperator) o;
            }
        }
        return testOk;
    }

    private static class Text {
        int endIndex;
        String text;

        public Text(int endIndex, String text) {
            this.endIndex = endIndex;
            this.text = text;
        }
    }
    // == != >= <= > < || &&
    private boolean isCharOk(char c) {
        return c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || c >= '0' && c <= '9';
    }

    public static void main(String[] args) {
//        String test = "name == 1";
        String test = "a == ''";
//        String test = "name == 10 && (ssss == 20 || (abc == 1 || a == ' ' ))";
        ConditionGroup cg = new ConditionGroup(test, 0);

        Map<String, Object> map = new HashMap<>();
        map.put("name", 1);
        map.put("ssss", 10);
        map.put("a", "");
        MapWrapper mapWrapper = new MapWrapper(map);
        MetaObject metaObject = new MetaObject(mapWrapper);
        System.out.println(cg.test(metaObject));
    }
}
