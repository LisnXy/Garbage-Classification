package com.flatangle.rubbishsearch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.flatangle.rubbishsearch.mapper")
public class RubbishsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(RubbishsearchApplication.class, args);
    }

}
