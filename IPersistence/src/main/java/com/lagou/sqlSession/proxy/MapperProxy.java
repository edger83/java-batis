package com.lagou.sqlSession.proxy;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import com.lagou.sqlSession.SqlSession;
import com.lagou.sqlSession.anns.Param;
import com.lagou.sqlSession.meta.BeanNull;
import com.lagou.sqlSession.meta.BeanWrapper;
import com.lagou.sqlSession.meta.MapWrapper;
import com.lagou.sqlSession.meta.ObjectWrapper;
import com.lagou.sqlSession.typehandler.BaseType;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapperProxy implements InvocationHandler {
    private SqlSession sqlSession;
    private Configuration configuration;

    public MapperProxy(SqlSession sqlSession, Configuration configuration) {
        this.sqlSession = sqlSession;
        this.configuration = configuration;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        String className = method.getDeclaringClass().getName();

        String statementId = className+"."+methodName;
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        if (mappedStatement == null) {
            throw new RuntimeException("未找到 statementId="+statementId);
        }
        ObjectWrapper objectWrapper = null;
        if (args == null) {

        } else if (args.length == 1) { // beanWrapper length == 1;
            objectWrapper = new BeanWrapper(args[0]);
        } else {
            Map<String, Object> map = new HashMap<>();
            Param[] params = paramanns(method);
            Class<?> parameterTypes[] = method.getParameterTypes();

            for (int i = 0; i < parameterTypes.length; i++) {
                Param param = params[i];
                Class<?> parameterType = parameterTypes[i];
                if (!BaseType.ok(parameterType)) {
                    // 限制一些，因为主要是简单实现步骤，不想太繁琐创建多参数实例过程。
                    throw new RuntimeException("多参类型不允许存在实例bean");
                }

                Object o = args[i];
                if (o == null) {
                    o = BeanNull.NULL;
                }
                map.put("["+i+"]", o);
                if (param != null) {
                    map.put(param.value(), o);
                }

            }
            objectWrapper = new MapWrapper(map);
        }


        String tagName = mappedStatement.getTagName();
        if (tagName.equals("select")) {
            if (method.getReturnType() == List.class) {
                return sqlSession.selectList(statementId, objectWrapper);
            } else {
                return sqlSession.selectOne(statementId, objectWrapper);
            }
        } else if (tagName.equals("update")) {
            return sqlSession.update(statementId, objectWrapper);
        }else if (tagName.equals("delete")) {
            return sqlSession.delete(statementId, objectWrapper);
        }else if (tagName.equals("insert")) {
            return sqlSession.insert(statementId, objectWrapper);
        } else {
            throw new RuntimeException("未定义功能："+ tagName);
        }

    }

    Param[] paramanns(Method method) {
        Annotation[][] all = method.getParameterAnnotations();
        Param[] result = new Param[all.length];
        for (int i = 0; i < all.length; i++) {
            for (int i1 = 0; i1 < all[i].length; i1++) {
                if (all[i][i1].annotationType() == Param.class) {
                    Param param = (Param) all[i][i1];
                    result[i] = param;
                }
            }
        }
        return result;
    }
}
