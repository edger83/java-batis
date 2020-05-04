package com.lagou.sqlSession.sqlnode.condition;

import com.lagou.sqlSession.meta.BeanNull;
import com.lagou.sqlSession.meta.MetaObject;

import java.math.BigDecimal;
import java.util.Date;

public class ConditionItem implements  Condition{

    private String leftText;
    private String symbol; // == != > < <= >=
    private String rightText;  // 值

    public ConditionItem(String leftText, String symbol, String rightText) {
        this.leftText = leftText;
        this.symbol = symbol;
        this.rightText = rightText;
    }

    public boolean test(MetaObject metaObject) {
        try {
            Object value1 = metaObject.getValue(leftText);
            if (value1 == null || value1 == BeanNull.NULL) {
                if (symbol.equals("!=")) {
                    return !rightText.equals("null");
                } else if (symbol.equals("==")) {
                    return rightText.equals("null");
                } else {
                    throw new RuntimeException("null 只接受 == != 符号");
                }
            }
            Class<?> type = value1.getClass();
            if (!Comparable.class.isAssignableFrom(type)) {
                throw new RuntimeException("当前字段必须是 Comparable 实现");
            }

            Object value2 = null;
            if (rightText.charAt(0) == '\'') {
                value2 = getObject(rightText.substring(1, rightText.length()-1), type);
            } else {
                value2 = metaObject.getValue(rightText);
                if (value2 == null) {
                    value2 = getObject(rightText, type);
                } else if (!(value2 instanceof Comparable)) {
                    throw new RuntimeException("当前字段必须是 Comparable 实现");
                }
            }
            if (value2 == null) {
                return false;
            }
            int x = ((Comparable)value1).compareTo(value2);

            if (symbol.equals("==")) {
                return x == 0;
            }
            if (symbol.equals("!=")) {
                return x != 0;
            }
            if (symbol.equals(">=")) {
                return x >= 0;
            }
            if (symbol.equals("<=")) {
                return x <= 0;
            }
            if (symbol.equals(">")) {
                return x > 0;
            }
            if (symbol.equals("<")) {
                return x < 0;
            }
            return false;
        } catch (Exception e) {

            throw new RuntimeException("发生异常" ,e);
        }
    }

    private Object getObject(String text, Class<?> type) {
        if (type == String.class) {
            return text;
        }
        if (type == boolean.class || type == Boolean.class) {
            return text.equals("true");
        }
        if (type == byte.class || type == Byte.class) {
            return (byte)Integer.parseInt(text);
        }
        if (type == short.class || type == Short.class) {
            return (short)Integer.parseInt(text);
        }
        if (type == int.class || type == Integer.class) {
            return Integer.parseInt(text);
        }
        if (type == float.class || type == Float.class) {
            return Float.parseFloat(text);
        }
        if (type == long.class || type == Long.class) {
            return Long.parseLong(text);
        }
        if (type == double.class || type == Double.class) {
            return Double.parseDouble(text);
        }
        if (type == char.class || type == Character.class) {
            return text.length() == 0 ? ' ' : text.charAt(0);
        }
        if (type == Date.class) {
            return new Date(Long.parseLong(text));
        }
        if (type == java.sql.Date.class) {
            return new java.sql.Date(Long.parseLong(text));
        }
        if (type == BigDecimal.class) {
            return new BigDecimal(text);
        }
       // ...
        return null;

    }
    public static void main(String[] args) {
        int x = 1;
        Comparable<Integer> d = (Comparable<Integer>) x;
        System.out.println(d.compareTo(2));
        System.out.println(d.compareTo(1));
    }

    /**
     * 逻辑运算符 && ||
     */
    public static class LogicalOperator {
        String name;

        public LogicalOperator(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
        public boolean test(boolean o1, Condition o2, MetaObject metaObject) {
            if (name.equals("||")) {
                return o1 || o2.test(metaObject);
            }
            if (name .equals("&&")) {
                return o1 && o2.test(metaObject);
            }
            throw new RuntimeException("逻辑运算符错误 ！");
        }

    }
}
