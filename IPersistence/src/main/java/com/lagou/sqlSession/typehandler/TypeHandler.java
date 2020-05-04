package com.lagou.sqlSession.typehandler;

import java.sql.PreparedStatement;

/**
 * 类型设置处理
 */
public interface TypeHandler {
    void setParameter(PreparedStatement ps, int i, Object parameter) throws Exception;
}
