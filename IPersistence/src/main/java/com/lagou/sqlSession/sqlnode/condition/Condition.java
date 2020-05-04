package com.lagou.sqlSession.sqlnode.condition;

import com.lagou.sqlSession.meta.MetaObject;

public interface Condition {

    boolean test(MetaObject metaObject);
}