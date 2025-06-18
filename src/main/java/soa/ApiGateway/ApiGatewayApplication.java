package soa.ApiGateway;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class ApiGatewayApplication {

	public static Logger logger = LoggerFactory.getLogger(ApiGatewayApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}