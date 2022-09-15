package com.fedag.CSR;

import com.fedag.CSR.controller.AuthController;
import com.fedag.CSR.service.impl.UserAuthImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class CsrApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsrApplication.class, args);
	}

}
