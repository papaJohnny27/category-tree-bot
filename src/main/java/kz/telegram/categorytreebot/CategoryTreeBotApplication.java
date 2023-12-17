package kz.telegram.categorytreebot;

import kz.telegram.categorytreebot.config.TelegramBotProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TelegramBotProperties.class)
public class CategoryTreeBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(CategoryTreeBotApplication.class, args);
	}

}
