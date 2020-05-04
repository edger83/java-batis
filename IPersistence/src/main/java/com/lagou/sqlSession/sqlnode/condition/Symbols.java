package com.lagou.sqlSession.sqlnode.condition;

/**
 *  2020-03-27 符号字典
 */
public class Symbols {
    public static Symbols create(String ...symbols) {
        Symbols symbolsx = new Symbols();
        symbolsx.adds(symbols);
        return symbolsx;
    }

    SymbolNode root = new SymbolNode(' ');

    public void add(String symbol) {
        SymbolNode node = root;
        for (int i = 0 ; i < symbol.length(); i ++) {
            char c = symbol.charAt(i);
            node = node.getOrCreate(c);
        }
        node.setEnd(true);
    }
    public void adds(String... symbols) {
        for (String symbol : symbols) {
            add(symbol);
        }
    }

    public SymbolText searchOne(String text, int start) {
        return searchOne(text, start, text.length());
    }
    public SymbolText searchOne(String text, int start, int end) {
        if (end > text.length()) {
            end = text.length();
        }
        if (start >= end) {
            return null;
        }

        SymbolNode node = root;
        int endIndex = -1;
        int startIndex = -1;
        int markStart = start;
        while (start < end) {
            node = node.next(text.charAt(start));
            if (node != null) {
                if (startIndex == -1) {
                    startIndex = start;
                }
                if (node.isEnd()) {
                    endIndex = start;
                }
                start ++; // 下一次继续
            } else if (endIndex != -1) {
                break;
            } else {
                markStart++;
                start = markStart;
                endIndex = -1;
                startIndex = -1;
                node = root;
            }
        }
        if (endIndex != -1) {
            return new SymbolText(startIndex, endIndex, text.substring(startIndex, endIndex + 1));
        }
        return null;
    }

    public static void main(String[] args) {
        Symbols symbols = new Symbols();
        symbols.adds("==", "!=","<",">",">=","<=");

        System.out.println(symbols.searchOne("==asdad==ad", 0));
        System.out.println(indexLeftChar('(', "   (d", 0));
    }


    public static int indexLeftChar(char c, String text, int start) {
        for (int i = start; i<text.length(); i++) {
            char c1= text.charAt(i);
            if (c1 != ' ') {
                return c1 == c ? i : -1;
            }
        }
        return -1;
    }
}
