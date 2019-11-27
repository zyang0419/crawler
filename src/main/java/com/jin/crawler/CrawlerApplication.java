package com.jin.crawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
//20191127添加注释
@SpringBootApplication(scanBasePackages = "com.jin.crawler")
@EnableTransactionManagement(proxyTargetClass = true)
public class CrawlerApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(CrawlerApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(CrawlerApplication.class, args);
	}
}
