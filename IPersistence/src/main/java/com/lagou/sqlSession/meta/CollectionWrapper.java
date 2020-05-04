package com.lagou.sqlSession.meta;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CollectionWrapper implements ObjectWrapper {
    Map<String, Object> map = new HashMap<>();
    Collection collection;
    Object arrays;
    public CollectionWrapper(Object arrays) {
        if (arrays == null) {
            this.collection = new ArrayList();
        } if (arrays instanceof Collection) {
            this.collection = collection;
        } else {
            this.collection = new ArrayList();
            if (arrays.getClass().isArray()) {
                int length = Array.getLength(arrays);
                for (int i = 0; i< length; i++) {
                    Object o = Array.get(arrays, i);
                    collection.add(o);
                }
            }
        }
        int index = 0;
        for (Object o : collection) {
            if (o == null) {
                o = BeanNull.NULL;
            }
            map.put(""+index, o);

        }
    }

    @Override
    public Object get(String name) throws Exception {
        return map.get(name);
    }

    @Override
    public Class<?> getType() {
        if (arrays == null) {
            return null;
        }
        return arrays.getClass();
    }
}
