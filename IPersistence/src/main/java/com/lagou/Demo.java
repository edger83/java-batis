package com.lagou;

import com.lagou.dao.ITestDao;
import com.lagou.io.Resources;
import com.lagou.pojo.TestBean;
import com.lagou.sqlSession.SqlSession;
import com.lagou.sqlSession.SqlSessionFactory;
import com.lagou.sqlSession.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * 最终使用测试
 *
 */
public class Demo {

    public static void main(String[] args) throws Exception{
        InputStream in = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory f = new SqlSessionFactoryBuilder().build(in);

        try (SqlSession sqlSession = f.openSession(false);) {

            out("直接查询"+sqlSession.selectOne("com.lagou.dao.ITestDao.selectNameById", new TestBean(56)));
            out("直接查询"+sqlSession.selectOne("com.lagou.dao.ITestDao.selectNameById", 56)); //

            // out("直接查询"+sqlSession.selectOne("com.lagou.dao.ITestDao.selectNameByTestId", 56)); 有传参数限制时 发生异常参数不匹配。

            // ITestDao

            TestBean testBean = new TestBean();
            testBean.setTestId(18);
            testBean.setTestName("time:" + System.currentTimeMillis());
            ITestDao testDao = sqlSession.getMapper(ITestDao.class);
            // 多种传参数方式实验
            out("无参数查询：" + testDao.selectNames());
            out("bean参数更新:" + testDao.update(testBean));
            out("bean参数查询:" + testDao.selectNameByTestId(testBean));
            out("方法参数查询:" + testDao.selectNameById(45));
            out("方法多参数查询:" + testDao.selectNameByIdAndName(20, "zxcvbnm"));
            out("方法参数注解查询:" + testDao.selectNameByIdAndName2(20, "zxcvbnm"));
            out("方法参数注解查询:" + testDao.selectNameByIdAndName2(18, null)); // <if test="testName!=null"></if>
            out("方法参数删除:" + testDao.delete(18));
            out("bean参数增加:" + testDao.insert(testBean));


            sqlSession.commit(); // 提交 sqlSession.rollback();
        }
       // sqlSession.close();  // 关闭
    }

    static void out(Object o) {
        System.out.println(o);
    }
}
