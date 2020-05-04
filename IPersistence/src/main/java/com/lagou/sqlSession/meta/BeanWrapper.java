package com.lagou.sqlSession.meta;

import com.lagou.sqlSession.typehandler.BaseType;

import java.lang.reflect.Field;

public class BeanWrapper implements  ObjectWrapper {
    private Object object;

    public BeanWrapper(Object object) {
        this.object = object;
    }

    @Override
    public Object get(String name)  {
        if (object == null) {
            return null;
        }
        if (BaseType.ok(object.getClass())) {
            return object;
        }

        Object value = null;
        try {
            Field field = object.getClass().getDeclaredField(name);
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            value = field.get(object);
            if (value == null) {
                value = BeanNull.NULL;
            }
        } catch (Exception e) {  }

        return value;
    }

    @Override
    public Class<?> getType() {
        return object.getClass();
    }

}
