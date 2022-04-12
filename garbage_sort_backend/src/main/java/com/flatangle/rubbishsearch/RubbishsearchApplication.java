package com.flatangle.rubbishsearch;

import com.flatangle.rubbishsearch.POJO.params.MulterFile;
import com.flatangle.rubbishsearch.service.MultiProcessService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.InputStream;


@SpringBootApplication
@MapperScan("com.flatangle.rubbishsearch.mapper")
public class RubbishsearchApplication {


    public static void main(String[] args) {

        SpringApplication.run(RubbishsearchApplication.class, args);
    }

}
