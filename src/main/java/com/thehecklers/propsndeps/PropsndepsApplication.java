package com.thehecklers.propsndeps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ConfigurationPropertiesScan
public class PropsndepsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PropsndepsApplication.class, args);
	}

	@Bean
	@ConfigurationProperties(prefix = "supplier")
	BeverageSupplier supplier() {
		return new BeverageSupplier();
	}
}

@AllArgsConstructor
@RestController
@RequestMapping("/beverage")
class BevController {
	//	@Value("${beverage.name:Raktajino}")
//	private String name;
//	@Value("${beverage.brew-method:Bespoke ${beverage.name:Earl Grey, hot}}")
//	private String brewMethod;
	private final HotBeverage beverage;

	@GetMapping("/name")
	String getName() {
		return beverage.getName();
	}

	@GetMapping("/brewmethod")
	String getBrewMethod() {
		return beverage.getBrewMethod();
	}
}

@AllArgsConstructor
@RestController
@RequestMapping("/supplier")
class SupController {
	private final BeverageSupplier supplier;

	@GetMapping
	BeverageSupplier getSupplier() {
		return supplier;
	}
}

@Value
@ConfigurationProperties(prefix = "beverage")
class HotBeverage {
	String name, brewMethod;

	@ConstructorBinding
	public HotBeverage(String name, @Name("brew") String brewMethod) {
		this.name = name;
		this.brewMethod = brewMethod;
	}
}

@Data
class BeverageSupplier {
	String name, location;
}
