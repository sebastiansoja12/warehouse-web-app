package com.warehouse;

import com.warehouse.auth.configuration.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EntityScan(basePackages = {"com.warehouse.*"})
@EnableJpaRepositories(basePackages = {"com.warehouse.*"})
@ConfigurationPropertiesScan(basePackages = {"com.warehouse.*"})
@EnableConfigurationProperties(RsaKeyProperties.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
