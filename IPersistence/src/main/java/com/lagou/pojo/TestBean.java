package com.lagou.pojo;

public class TestBean {

    private long testId;
    private String testName;

    public TestBean() {
    }
    // 方便测试
    public TestBean(long testId, String testName) {
        this.testId = testId;
        this.testName = testName;
    }

    public TestBean(long testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public long getTestId() {
        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "testId=" + testId +
                ", testName='" + testName + '\'' +
                '}';
    }


}
