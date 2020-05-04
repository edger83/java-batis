package com.lagou.utils;

import com.lagou.sqlSession.typehandler.TypeHandler;
import com.lagou.sqlSession.typehandler.DefaultTypeHandler;

public class ParameterMapping {

    private String content;

    public ParameterMapping(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TypeHandler getDefaultTypeHandler() {
        return new DefaultTypeHandler();
    }
}
