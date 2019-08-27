package com.front.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/***
 **@project: base
 **@description: api  document
 **@Author: twj
 **@Date: 2019/06/20
 * /swagger-ui.html
 **/
@Configuration
public class SwaggerConfig {


    /***
     * swagger配置
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.front.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("swagger spring cloud alibaba version my app api ----文档")
                .description("api文档")
                .termsOfServiceUrl("http://www.fengxin.xyz")
                .version("1.0")
                .build();
    }
}
