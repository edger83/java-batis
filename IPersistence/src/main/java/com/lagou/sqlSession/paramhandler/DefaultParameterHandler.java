package com.lagou.sqlSession.paramhandler;


import com.lagou.config.BoundSql;
import com.lagou.pojo.MappedStatement;
import com.lagou.sqlSession.typehandler.JdbcType;
import com.lagou.sqlSession.typehandler.TypeHandler;
import com.lagou.sqlSession.meta.*;
import com.lagou.utils.ParameterMapping;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 * 默认参数设置处理
 */
public class DefaultParameterHandler implements ParameterHandler {
    private MappedStatement mappedStatement;
    private Object parameter;
    private BoundSql boundSql;

    public DefaultParameterHandler(MappedStatement mappedStatement, Object parameter, BoundSql boundSql) {
        this.mappedStatement = mappedStatement;
        this.parameter = parameter;
        this.boundSql = boundSql;
    }
    @Override
    public void setParameters(PreparedStatement ps) throws Exception {
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappingList();
        if (parameterMappings.size() == 0) {
            return;
        }
        MappedStatement ms = this.mappedStatement;
        MetaObject metaObject = null;
        if (parameter instanceof ObjectWrapper) {
            metaObject = new MetaObject((ObjectWrapper) parameter);
        } else if (parameter instanceof Map){
            metaObject = new MetaObject(new MapWrapper((Map<String, Object>) parameter));
        } else if (parameter instanceof Collection || parameter.getClass().isArray()) {
            metaObject = new MetaObject(new CollectionWrapper(parameter));
        } else {
            metaObject = new MetaObject(new BeanWrapper(parameter));
        }
        if (!metaObject.isType(ms.getParamterType())) {
            throw new RuntimeException("传入参数不匹配！！！");
        }
        for (int i = 0; i < parameterMappings.size(); i++) {
            ParameterMapping parameterMapping = parameterMappings.get(i);
            String name = parameterMapping.getContent();
            Object value = metaObject.getValue(name);
            if (value == null) {
                value = metaObject.getValue("["+i+"]"); // 尝试用下标获取
            }
            TypeHandler typeHandler = parameterMapping.getDefaultTypeHandler();
            typeHandler.setParameter(ps, i + 1, value);
        }
    }


    Class<?> getClassType(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }
}
