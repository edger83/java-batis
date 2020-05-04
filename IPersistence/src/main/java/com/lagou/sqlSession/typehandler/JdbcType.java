package com.lagou.sqlSession.typehandler;

public class JdbcType {
    private Class<?> type;
    private int sqlTypeId;

    public JdbcType(Class<?> type, int sqlTypeId) {
        this.type = type;
        this.sqlTypeId = sqlTypeId;
    }

    public Class<?> getType() {
        return type;
    }

    public int getSqlTypeId() {
        return sqlTypeId;
    }
}
