package com.lagou.sqlSession.typehandler;

import com.lagou.sqlSession.meta.BeanNull;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.SQLType;
import java.sql.Types;

/**
 * 默认实现一个类型设置处理
 */
public class DefaultTypeHandler implements TypeHandler {

    @Override
    public void setParameter(PreparedStatement ps, int i, Object parameter) throws Exception {
        if (parameter == null) {
            return;
        }
        if (parameter == BeanNull.NULL) {
            ps.setObject(i, null);
        } else {
            ps.setObject(i, parameter);
        }

    }
}
