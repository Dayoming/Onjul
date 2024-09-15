package com.tiffy;

import com.tiffy.repository.ItemRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TiffyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiffyApplication.class, args);
	}

	@Bean
	public TestDataInit testDateInit(ItemRepository itemRepository) {
		return new TestDataInit(itemRepository);
	}
}
