package com.lagou.sqlSession;


import com.lagou.config.BoundSql;
import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import com.lagou.sqlSession.paramhandler.DefaultParameterHandler;
import com.lagou.sqlSession.paramhandler.ParameterHandler;
import com.lagou.sqlSession.transaction.Transaction;
import com.lagou.utils.GenericTokenParser;
import com.lagou.utils.ParameterMapping;
import com.lagou.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

public class SimpleExecutor implements  Executor {
    private Configuration configuration;
    private Transaction transaction;
    public SimpleExecutor(Configuration configuration, Transaction transaction) {
        this.configuration = configuration;
        this.transaction = transaction;
    }
    public Connection getConnection() throws SQLException {

        return transaction.getConnection();
    }

    @Override                                                                                //user
    public <E> List<E> query(Configuration configuration, MappedStatement ms, Object parameter) throws Exception {
        // 1. 注册驱动，获取连接
        Connection connection = getConnection();

        // 2. 获取sql语句 : select * from user where id = #{id} and username = #{username}
            //转换sql语句： select * from user where id = ? and username = ? ，转换的过程中，还需要对#{}里面的值进行解析存储
        // String sql = ms.getSql();
        BoundSql boundSql = ms.getSqlSource().getBoundSql(parameter);
        ParameterHandler parameterHandler = new DefaultParameterHandler(ms, parameter, boundSql);
        // 3.获取预处理对象：preparedStatement
        try (PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText())) {
            parameterHandler.setParameters(preparedStatement);

            // 5. 执行sql
            ResultSet resultSet = preparedStatement.executeQuery();
            String resultType = ms.getResultType();
            Class<?> resultTypeClass = getClassType(resultType);

            ArrayList<Object> objects = new ArrayList<>();

            // 6. 封装返回结果集
            while (resultSet.next()) {
                if (resultTypeClass == Map.class) {
                    Map<String, Object> map = new LinkedHashMap<>();
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        String columnName = metaData.getColumnName(i);
                        Object value = resultSet.getObject(columnName);
                        map.put(columnName, value);
                    }
                    objects.add(map);
                } else if (resultTypeClass == String.class || resultTypeClass.isPrimitive()) {
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    if (metaData.getColumnCount() > 1) {
                        throw new RuntimeException("获取的列不是唯一");
                    }
                    Object value = resultSet.getObject(1);
                    objects.add(value);
                } else {
                    Object o = resultTypeClass.newInstance();
                    //元数据
                    ResultSetMetaData metaData = resultSet.getMetaData();

                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        // 字段名
                        String columnName = metaData.getColumnName(i);
                        // 字段的值
                        Object value = resultSet.getObject(columnName);
                        //使用反射或者内省，根据数据库表和实体的对应关系，完成封装
                        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                        Method writeMethod = propertyDescriptor.getWriteMethod();
                        writeMethod.invoke(o, value);


                    }
                    objects.add(o);
                }


            }
            return (List<E>) objects;
        }

    }

    private Class<?> getClassType(String paramterType) throws ClassNotFoundException {
        if(paramterType!=null){
            Class<?> aClass = Class.forName(paramterType);
            return aClass;
        }
         return null;

    }


//    /**
//     * 完成对#{}的解析工作：1.将#{}使用？进行代替，2.解析出#{}里面的值进行存储
//     * @param sql
//     * @return
//     */
//    private BoundSql getBoundSql(String sql) {
//        //标记处理类：配置标记解析器来完成对占位符的解析处理工作
//        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
//        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
//        //解析出来的sql
//        String parseSql = genericTokenParser.parse(sql);
//        //#{}里面解析出来的参数名称
//        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
//
//        BoundSql boundSql = new BoundSql(parseSql,parameterMappings);
//         return boundSql;
//
//    }


    @Override
    public int update(Configuration configuration, MappedStatement ms, Object parameter) throws Exception{
        // String sql = ms.getSql();
        BoundSql boundSql = ms.getSqlSource().getBoundSql(parameter); // 获得boundsql

        Connection connection = getConnection();
        ParameterHandler parameterHandler = new DefaultParameterHandler(ms, parameter, boundSql);
        try (PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText())) {
            parameterHandler.setParameters(preparedStatement);

            return preparedStatement.executeUpdate(); // 执行 sql 语句 insert update delete
        }
    }

    public Transaction getTransaction() {
        return transaction;
    }
}
