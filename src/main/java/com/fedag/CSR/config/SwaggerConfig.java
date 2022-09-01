package com.fedag.CSR.config;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

public class SwaggerConfig {

    public Docket swaggerDocumentation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.fedag"))
                .build()
                .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails() {
        return new ApiInfo(
                "Steam-based cases and skins trading platform",
                "API",
                "1.0",
                "Free to use",
                new Contact("FedAG", "http://fedag.com", "fedag@gmail.com"),
                "API Licence",
                "http://fedag.com",
                Collections.emptyList());
    }
}
