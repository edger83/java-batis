package com.lagou.sqlSession.sqlnode;

import com.lagou.sqlSession.sqlnode.condition.ConditionGroup;

import java.util.ArrayList;
import java.util.List;

public class IfNode implements  SqlNode{
    List<SqlNode> sqlNodes;
    String test;
    ConditionGroup conditionGroup = null;
    public IfNode(String test, List<SqlNode> sqlNodes) {
        this.test = test;
        this.sqlNodes = sqlNodes;
        conditionGroup = new ConditionGroup(test, 0);
    }

    @Override
    public void apply(SqlContext context) {
        if (conditionGroup.test(context.getMetaObject())) {
            for (SqlNode sqlNode : sqlNodes) {
                sqlNode.apply(context);
            }
        }
    }
    public void add(SqlNode node) {
        this.sqlNodes.add(node);
    }
}
