package com.fedag.CSR;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CsrApplication {
    public static void main(String[] args) {
        SpringApplication.run(CsrApplication.class, args);

    }
}
