package com.lagou.sqlSession;

import com.lagou.pojo.Configuration;
import com.lagou.sqlSession.transaction.JdbcTransaction;
import com.lagou.sqlSession.transaction.Transaction;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }


    @Override
    public SqlSession openSession() {
        return openSession(false);
    }

    @Override
    public SqlSession openSession(boolean autoCommit) {
        Transaction transaction = new JdbcTransaction(configuration, false);
        Executor executor = new SimpleExecutor(configuration, transaction);
        return new DefaultSqlSession(configuration, executor);
    }


}
