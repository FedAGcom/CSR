package com.fedag.CSR;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;

@SpringBootApplication
@EnableScheduling
public class CsrApplication {
    static public void main(String[] args) {
       SpringApplication.run(CsrApplication.class, args);
    }
}
