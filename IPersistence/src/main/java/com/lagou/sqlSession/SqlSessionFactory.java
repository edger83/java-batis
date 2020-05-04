package com.lagou.sqlSession;

public interface SqlSessionFactory {

    public SqlSession openSession();

    public SqlSession openSession(boolean autoCommit);


}
