package com.pandax.litemall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = "com.pandax.litemall.mapper")
@EnableTransactionManagement
public class LitemallApplication {

    public static void main(String[] args) {
        SpringApplication.run(LitemallApplication.class, args);
    }

}
