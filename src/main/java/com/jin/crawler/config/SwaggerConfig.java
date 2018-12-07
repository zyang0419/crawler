package com.jin.crawler.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${spring.profiles.active}")
    private String active;
 
    @Bean
    public Docket docket(){
        ApiSelectorBuilder builder = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
//                   当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.jin.crawler.*.controller"));
        if("dev".equals(active)){
            return
                    builder.paths(PathSelectors.any()).build();
        }else{
            /**
             * 如果是线上环境，添加路径过滤，设置为全部都不符合
             */
            return  builder.paths(PathSelectors.none()).build();
        }
    }

    /**
     * 构建api文档的详细信息函数
     * @return ApiInfo
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                //页面标题
                    .title("舆情监控系统使用Swagger2构建RESTful API")
                //创建人
                    .contact(new Contact("jin","http://www.qi-chuan.com","pzzxh001@163.com"))
                 //版本号
                    .version("1.0")
                //描述
                    .description("API 描述")
                    .build();
    }

  /*  private ApiInfo apiInfoOnline() {
        return new ApiInfoBuilder()
                .title("")
                .description("")
                .license("")
                .licenseUrl("")
                .termsOfServiceUrl("")
                .version("")
                .contact(new Contact("","", ""))
                .build();
    }*/

}