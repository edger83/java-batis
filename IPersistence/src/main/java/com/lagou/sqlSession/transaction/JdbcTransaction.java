package com.lagou.sqlSession.transaction;

import com.lagou.pojo.Configuration;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * JDBC事务处理
 */
public class JdbcTransaction implements Transaction {
    private Configuration configuration;
    private Connection connection;
    private boolean autoCommit; // 是否创建自动提交

    public JdbcTransaction(Configuration configuration, boolean autoCommit) {
        this.configuration = configuration;
        this.autoCommit = autoCommit;
    }
    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = configuration.getDataSource().getConnection();
            connection.setAutoCommit(autoCommit);
        }
        return connection;
    }

    @Override
    public void commit() throws SQLException {
        connection.commit();
    }

    @Override
    public void rollback() throws SQLException {
        connection.rollback();
    }

    @Override
    public void close() throws SQLException {

        connection.close();
    }
}
