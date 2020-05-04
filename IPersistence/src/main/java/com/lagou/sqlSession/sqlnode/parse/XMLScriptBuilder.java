package com.lagou.sqlSession.sqlnode.parse;

import com.lagou.io.Resources;
import com.lagou.pojo.Configuration;
import com.lagou.sqlSession.sqlnode.*;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;
import org.dom4j.tree.DefaultText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLScriptBuilder {
    Configuration configuration;
    Element context;

    public XMLScriptBuilder(Configuration configuration, Element context) {
        this.configuration = configuration;
        this.context = context;
    }

    public DynamicSqlSource parseScriptNode() {
        MixedSqlNode rootSqlNode = parseDynamicTags(context);
        DynamicSqlSource dynamicSqlSource = new DynamicSqlSource(configuration, rootSqlNode);
        return dynamicSqlSource;
    }
    protected MixedSqlNode parseDynamicTags(Element node) {
        List<SqlNode> sqlNodes = new ArrayList<>();
        MixedSqlNode mixedSqlNode = new MixedSqlNode(sqlNodes);
        handlerNode(node, sqlNodes);
        return mixedSqlNode;
    }
    protected void handlerNode(Element node,  List<SqlNode> sqlNodes) {
        List<Node> elements = node.content();
        for (Node element : elements) {
            if (element instanceof  DefaultText) {
                StaticTextNode textNode = new StaticTextNode(element.getText());
                sqlNodes.add(textNode);
            } else if (element instanceof DefaultElement) {
                String name = element.getName().toLowerCase();
                if (name.equals("if")) {
                    String test = ((Element)element).attributeValue("test");
                    List<SqlNode> childrenNodes = new ArrayList<>();
                    IfNode ifNode = new IfNode(test, childrenNodes);
                    sqlNodes.add(ifNode);
                    handlerNode((Element) element, childrenNodes);
                } else {
                    throw new RuntimeException("暂不支持当前标签：" + name);
                }

            }
        }
    }

    public static void main(String[] args) throws  Exception{
        XMLScriptBuilder x = new XMLScriptBuilder(null, null);
        Document document = new SAXReader().read(Resources.getResourceAsSteam("xx.xml"));
        Map<String, Object> map = new HashMap<>();
        map.put("name", 1);
        SqlContext context = new SqlContext(map);
        x.parseDynamicTags(document.getRootElement()).apply(context);
        System.out.println(context);
    }
}
