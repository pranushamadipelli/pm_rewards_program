package com.charter.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@SpringBootApplication(scanBasePackages = { "com.charter" })
@EnableJpaRepositories()
//@EnableAspectJAutoProxy
public class CharterRewardsProgramApplication {

	public static void main(String[] args) {
		SpringApplication.run(CharterRewardsProgramApplication.class, args);
	}

	
	@Bean
	public OpenAPI customOpenAPI(@Value("${application.version}") String appVersion,
			@Value("${prod.server.url}") String prodServerUrl) {
		Server prodServer = null;
		try {
			prodServer = new Server();
			prodServer.setUrl(prodServerUrl);
			prodServer.setDescription("Server URL in Production environment");
		} catch (Exception e) {
		}
		return new OpenAPI().info(new Info()
				.contact(new Contact())
				.title("Rewards Program Engine").version(appVersion).description("Rewards Program APIs")).servers(List.of(prodServer));
	}
}
