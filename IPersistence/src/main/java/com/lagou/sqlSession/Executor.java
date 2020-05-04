package com.lagou.sqlSession;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import com.lagou.sqlSession.transaction.Transaction;

import java.util.List;

public interface Executor {

    public <E> List<E> query(Configuration configuration,MappedStatement mappedStatement,Object parameter) throws Exception;

    int update(Configuration configuration, MappedStatement ms, Object parameter) throws Exception;

    public Transaction getTransaction();

}
