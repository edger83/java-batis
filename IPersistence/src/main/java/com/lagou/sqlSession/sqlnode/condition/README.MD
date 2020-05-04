条件选择：

```java
String test = "name == 10 && (ssss == 20 || (abc == 1 || a == 3 ))";
ConditionGroup cg = new ConditionGroup(test, 0);

Map<String, Object> map = new HashMap<>();
map.put("name", 10);
map.put("ssss", 10);
map.put("a", "3");
MapWrapper mapWrapper = new MapWrapper(map);
MetaObject metaObject = new MetaObject(mapWrapper);
System.out.println(cg.test(metaObject)); // true
```

