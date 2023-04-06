package com.moklyak.platformserver.configuration;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import java.io.File;
import java.nio.file.Path;


@Component
public class MyInitializingBean implements InitializingBean {



    @Override
    public void afterPropertiesSet() throws Exception {
        File f = new File(Path.of("./temp/calc.jar").toUri());
        f.getParentFile().mkdirs();
        
    }
}