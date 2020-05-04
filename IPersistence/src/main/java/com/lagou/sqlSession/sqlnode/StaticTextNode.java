package com.lagou.sqlSession.sqlnode;

public class StaticTextNode implements SqlNode {
    private String text; //

    public StaticTextNode(String text) {
        this.text = text;
    }

    @Override
    public void apply(SqlContext context) {
        context.append(text);
    }
}
