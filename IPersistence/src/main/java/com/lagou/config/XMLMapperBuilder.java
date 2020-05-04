package com.lagou.config;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import com.lagou.sqlSession.sqlnode.SqlSource;
import com.lagou.sqlSession.sqlnode.parse.XMLScriptBuilder;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration =configuration;
    }

    public void parse(InputStream inputStream) throws DocumentException {

        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();

        String namespace = rootElement.attributeValue("namespace");

        List<Element> list = rootElement.selectNodes("select|update|delete|insert");
        for (Element element : list) {
            XMLScriptBuilder scriptBuilder = new XMLScriptBuilder(configuration, element);
            SqlSource sqlSource = scriptBuilder.parseScriptNode();
            boolean select = element.getName().equals("select");

            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType", select ? "java.util.Map" : "java.lang.Integer");
            String paramterType = element.attributeValue("paramterType");
            String sqlText = element.getTextTrim();
            String tagName = element.getName();
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setResultType(resultType);
            mappedStatement.setParamterType(paramterType);
            mappedStatement.setSqlSource(sqlSource);
            mappedStatement.setTagName(tagName);
            String key = namespace+"."+id;
            configuration.getMappedStatementMap().put(key,mappedStatement);

        }

    }


}
