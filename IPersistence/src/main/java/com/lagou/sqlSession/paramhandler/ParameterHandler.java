package com.lagou.sqlSession.paramhandler;


import java.sql.PreparedStatement;

/**
 * 参数设置
 */
public interface ParameterHandler {
    /**
     * 调用此方法给这个Sql请求设置参数。
     * @param ps
     * @throws Exception
     */
    void setParameters(PreparedStatement ps)
            throws Exception;
}
