package com.lagou.sqlSession;

import java.util.List;

public interface SqlSession extends AutoCloseable{

    //查询所有
    public <E> List<E> selectList(String statementid, Object parameter) throws Exception;

    //根据条件查询单个
    public <T> T selectOne(String statementid, Object parameter) throws Exception;


    //为Dao接口生成代理实现类
    public <T> T getMapper(Class<?> mapperClass);


    int insert(String statement, Object parameter) throws Exception;

    int update(String statement, Object parameter) throws Exception;

    int delete(String statement, Object parameter) throws Exception;

    void commit() throws Exception;
    void rollback() throws Exception;
    void close() throws Exception;
}
