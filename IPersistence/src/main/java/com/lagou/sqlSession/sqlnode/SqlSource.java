package com.lagou.sqlSession.sqlnode;

import com.lagou.config.BoundSql;

public interface SqlSource {
    BoundSql getBoundSql(Object parameterObject);
}
