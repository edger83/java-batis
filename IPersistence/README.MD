测试实例：com.lagou.Demo

举例说明：

```java
public interface ITestDao {
	int update(TestBean bean);
  int insert(TestBean bean);
  int delete(@Param("testId") long testId);
  List<TestBean> select();
  List<TestBean> selectByIdAndName(long testId, String name);
  List<TestBean> selectByIdAndName2(TestBean bean);
  TestBean selectById(TestBean bean); 
  String selectNameById(@Param("testId") long testId); 
}

```

使用

```java
SqlSession session = ...
// 如果设置的parameterType 则传入错误类型会异常
select.selectOne("ITestDao.selectNameById", 34);
select.selectList("ITestDao.selectNameById", new TestBean(34));
// ITestDao
ITestDao td = session.getMapper(ITestDao.class);
String name = td.selectNameById(18);
List<TestBean> all = td.select();
int updateRes = td.update(new TestBean(10,"oooo"));
```

