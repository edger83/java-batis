package com.lagou.io;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Resources {

    public static InputStream getResourceAsSteam(String path) {
        InputStream resourceAsStream = Resources.class.getClassLoader().getResourceAsStream(path);
        if (resourceAsStream == null) {
            throw new RuntimeException(new FileNotFoundException("path:" + path));
        }
        return  resourceAsStream;

    }



}
