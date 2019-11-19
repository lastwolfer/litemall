package com.pandax.litemall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = "com.pandax.litemall.mapper")
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class LitemallApplication {

    public static void main(String[] args) {
        SpringApplication.run(LitemallApplication.class, args);
    }

}
