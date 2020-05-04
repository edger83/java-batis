package com.lagou.sqlSession.sqlnode.condition;

import java.util.HashMap;
import java.util.Map;

public class SymbolNode {
    char c; // 符号节点
    boolean end; // 结束点
    Map<Character, SymbolNode> nextChars = new HashMap<>();

    public SymbolNode(char c) {
        this.c = c;
    }


    public SymbolNode getOrCreate(char c) {
        SymbolNode symbolNode = nextChars.get(c);
        if (symbolNode == null) {
            symbolNode = new SymbolNode(c);
            nextChars.put(c, symbolNode);
        }
        return symbolNode;
    }

    public SymbolNode next(char c) {
        SymbolNode symbolNode = this.nextChars.get(c);
        return symbolNode;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }
}
