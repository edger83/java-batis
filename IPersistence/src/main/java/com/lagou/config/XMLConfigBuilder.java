package com.lagou.config;

import com.lagou.io.Resources;
import com.lagou.pojo.Configuration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.sql.DataSource;
import java.beans.PropertyDescriptor;
import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder() {
        this.configuration = new Configuration();
    }

    /**
     * 该方法就是使用dom4j对配置文件进行解析，封装Configuration
     */
    public Configuration parseConfig(InputStream inputStream) throws DocumentException, PropertyVetoException {

        Document document = new SAXReader().read(inputStream);
        //<configuration>
        Element rootElement = document.getRootElement();
        Element dataSourceElement = rootElement.element("dataSource");
        List<Element> list = dataSourceElement.elements("property");
        Properties properties = new Properties();
        for (Element element : list) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name,value);
        }

        String dataSourceClassName = dataSourceElement.attributeValue("class");
        DataSource dataSource = parseDataSource(dataSourceClassName, properties);
        configuration.setDataSource(dataSource);

        //mapper.xml解析: 拿到路径--字节输入流---dom4j进行解析
        List<Element> mapperList = rootElement.selectNodes("//mapper");

        for (Element element : mapperList) {
            String mapperPath = element.attributeValue("resource");
            InputStream resourceAsSteam = Resources.getResourceAsSteam(mapperPath);
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            xmlMapperBuilder.parse(resourceAsSteam);

        }




        return configuration;
    }


    public DataSource parseDataSource(String dataSourceClassName, Properties properties) {
        try {
            Class<?> dataSourceType = Class.forName(dataSourceClassName);
            DataSource dataSource = (DataSource) dataSourceType.newInstance();
            Enumeration<?> enumeration = properties.propertyNames();
            while (enumeration.hasMoreElements()) {
                String key = (String) enumeration.nextElement();
                String value = properties.getProperty(key);
                if (value != null) {
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(key, dataSourceType);
                    Method writeMethod = propertyDescriptor.getWriteMethod();
                    if (writeMethod != null) {
                        writeMethod.setAccessible(true);
                        writeMethod.invoke(dataSource, typeToObject(value, writeMethod.getParameterTypes()[0]));
                    }
                }

            }

            return dataSource;
        } catch (Exception e) {
            throw new RuntimeException("创建数据源失败:" + e);
        }
    }

    private Object typeToObject(String value, Class<?> type) {
        if (type == int.class || type == Integer.class) {
            return Integer.parseInt(value);
        }
        if (type == long.class || type == Long.class) {
            return Long.parseLong(value);
        }
        if (type == short.class || type == Short.class) {
            return (short)Integer.parseInt(value);
        }
        if (type == byte.class || type == Byte.class) {
            return (byte)Integer.parseInt(value);
        }
        if (type == double.class || type == Double.class) {
            return Double.parseDouble(value);
        }
        if (type == float.class || type == Float.class) {
            return (float)Double.parseDouble(value);
        }
        // ....
        return value;
    }
}
