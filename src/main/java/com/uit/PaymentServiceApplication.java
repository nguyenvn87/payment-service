package com.uit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@SpringBootApplication
@EnableFeignClients
public class PaymentServiceApplication {

	public static void main(String[] args) {

		String original = "customer-vietqrtest-bive:Vietqrtestbive";
		String encoded = Base64.getEncoder()
				.encodeToString(original.getBytes());
		System.out.println(encoded);

		SpringApplication.run(PaymentServiceApplication.class, args);
	}

}
