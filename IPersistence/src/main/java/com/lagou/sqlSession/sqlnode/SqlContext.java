package com.lagou.sqlSession.sqlnode;

import com.lagou.pojo.MappedStatement;
import com.lagou.sqlSession.meta.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SqlContext {
    private MetaObject metaObject;
    private Object parameter;
    private StringBuilder context = new StringBuilder();
    public SqlContext(Object parameter) {
        this.parameter = parameter;
        if (parameter == null) {
            metaObject = new MetaObject(new MapWrapper(new HashMap<>()));
        } else if (parameter instanceof ObjectWrapper) {
            metaObject = new MetaObject((ObjectWrapper) parameter);
        } else if (parameter instanceof Map){
            metaObject = new MetaObject(new MapWrapper((Map<String, Object>) parameter));
        } else if (parameter instanceof Collection || parameter.getClass().isArray()) {
            metaObject = new MetaObject(new CollectionWrapper(parameter));
        } else {
            metaObject = new MetaObject(new BeanWrapper(parameter));
        }
    }


    public void append(String text) {
        context.append(text);
    }

    public MetaObject getMetaObject() {
        return metaObject;
    }

    @Override
    public String toString() {
        return context.toString();
    }
}
