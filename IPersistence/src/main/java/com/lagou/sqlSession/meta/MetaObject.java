package com.lagou.sqlSession.meta;

public class MetaObject {

    ObjectWrapper objectWrapper;

    public MetaObject(ObjectWrapper objectWrapper) {
        this.objectWrapper = objectWrapper;
    }

    public Object getValue(String name) throws Exception{
        return this.objectWrapper.get(name);
    }

    /**
     * 类型为 null 则可以为任何类型的参数
     * @param className
     * @return
     */
    public boolean isType(String className) {

        return className == null || className.equals(objectWrapper.getType().getName());
    }
}
