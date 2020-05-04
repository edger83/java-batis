package com.lagou.sqlSession.meta;

public interface ObjectWrapper {
    Object get(String name) throws Exception;
    Class<?> getType();
}
