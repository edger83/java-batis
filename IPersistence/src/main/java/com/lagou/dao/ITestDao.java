package com.lagou.dao;

import com.lagou.pojo.TestBean;
import com.lagou.sqlSession.anns.Param;

import java.util.List;

public interface ITestDao {

    List<String> selectNames(); // 查询所有
    String selectNameByTestId(TestBean testBean); // 使用 bean 封装Id查询
    String selectNameById(long testId); // 使用参数的ID查询
    String selectNameByIdAndName(long testId, String testName); // 使用多参数顺序查询
    TestBean selectNameByIdAndName2(@Param("testId") long testId, @Param("testName") String testName); // 使用注解查询

    int update(TestBean testBean); // 使用bean更新

    int insert(TestBean testBean);// 使用bean更新

    int delete(long testId); // 使用 参数的值 删除
}
