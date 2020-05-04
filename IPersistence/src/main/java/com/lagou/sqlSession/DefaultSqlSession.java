package com.lagou.sqlSession;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import com.lagou.sqlSession.proxy.MapperProxy;

import java.lang.reflect.*;
import java.util.List;

public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;
    private Executor executor;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    @Override
    public <E> List<E> selectList(String statementid, Object parameter) throws Exception {

        //将要去完成对simpleExecutor里的query方法的调用
        // SimpleExecutor SimpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementid);
        List<Object> list = executor.query(configuration, mappedStatement, parameter);

        return (List<E>) list;
    }

    @Override
    public <T> T selectOne(String statementid, Object parameter) throws Exception {
        List<Object> objects = selectList(statementid, parameter);
        if(objects.size()==1){
            return (T) objects.get(0);
        }else if (objects.size() > 1){
            throw new RuntimeException("返回结果过多");
        } else {
            return null;
        }


    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        // 使用JDK动态代理来为Dao接口生成代理对象，并返回

        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(),
                new Class[]{mapperClass}, new MapperProxy(this, configuration));

        return (T) proxyInstance;
    }

    @Override
    public int insert(String statementid, Object parameter) throws Exception {


        return update(statementid, parameter);
    }

    @Override
    public int update(String statementid, Object parameter) throws Exception {

        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementid);
        return executor.update(this.configuration, mappedStatement, parameter);
    }

    @Override
    public int delete(String statementid, Object parameter) throws Exception {

        return update(statementid, parameter);
    }

    @Override
    public void commit() throws Exception {
        executor.getTransaction().commit();
    }

    @Override
    public void rollback() throws Exception {
        executor.getTransaction().rollback();
    }

    @Override
    public void close() throws Exception {
        executor.getTransaction().close();
    }


}
