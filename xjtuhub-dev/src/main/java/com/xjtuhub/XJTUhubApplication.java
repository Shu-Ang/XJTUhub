package com.xjtuhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.xjtuhub.mapper")
public class XJTUhubApplication {
    public static void main(String[] args) {
        SpringApplication.run(XJTUhubApplication.class, args);
    }
}
