package com.onjul.onjul;

import com.onjul.onjul.repository.ItemRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnjulApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnjulApplication.class, args);
	}

	@Bean
	public TestDataInit testDateInit(ItemRepository itemRepository) {
		return new TestDataInit(itemRepository);
	}
}
