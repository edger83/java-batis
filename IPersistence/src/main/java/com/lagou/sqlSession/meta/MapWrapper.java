package com.lagou.sqlSession.meta;

import java.util.Map;

public class MapWrapper implements  ObjectWrapper {
    private Map<String, Object> map;

    public MapWrapper(Map<String, Object> map) {
        this.map = map;
    }
    @Override
    public Object get(String name) throws Exception {
        return map.get(name);
    }

    @Override
    public Class<?> getType() {
        return Map.class;
    }
}
