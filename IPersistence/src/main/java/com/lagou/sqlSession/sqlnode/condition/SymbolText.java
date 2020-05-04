package com.lagou.sqlSession.sqlnode.condition;

public class SymbolText {

    private int startIndex;
    private int endIndex;
    private String text;

    public SymbolText(int startIndex, int endIndex, String text) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.text = text;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "SymbolText{" +
                "startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                ", text='" + text + '\'' +
                '}';
    }

    public boolean is(String... names) {
        for (String name : names) {
            if (text.equals(name)) {
                return true;
            }
        }
        return false;
    }
}
