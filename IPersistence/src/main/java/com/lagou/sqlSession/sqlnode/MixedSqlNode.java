package com.lagou.sqlSession.sqlnode;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MixedSqlNode implements SqlNode {
    public MixedSqlNode(List<SqlNode> sqlNodes) {
        this.sqlNodes = sqlNodes;
    }

    List<SqlNode> sqlNodes = new ArrayList<>();

    @Override
    public void apply(SqlContext context) {
        for (SqlNode sqlNode : sqlNodes) {
            sqlNode.apply(context);
        }
    }
}
