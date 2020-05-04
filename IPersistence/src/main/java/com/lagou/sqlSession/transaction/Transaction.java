package com.lagou.sqlSession.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 事务处理实现
 */
public interface Transaction {

    Connection getConnection() throws SQLException;


    void commit() throws SQLException;


    void rollback() throws SQLException;


    void close() throws SQLException;
}
