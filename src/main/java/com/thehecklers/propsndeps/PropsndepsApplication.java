package com.thehecklers.propsndeps;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
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
	@ConfigurationProperties(prefix = "blackbox")
	BlackBoxProperties bbProps() {
		return new BlackBoxProperties();
	}
}

@RestController
@RequestMapping("/bb")
@AllArgsConstructor
class BlackBoxController {
	private final BlackBoxProperties bbProps;

	@GetMapping
	BlackBoxProperties getBbProps() {
		return bbProps;
	}
}

@RestController
@RequestMapping("/dev")
class DeveloperController {
	@Value("${developer.first-name:Jenn}")
	private String devName;

	@Value("${developer.recognition}")
	private String award;

	@GetMapping("/name")
	String getDevName() {
		return devName;
	}

	@GetMapping("/award")
	String getDevAward() {
		return award;
	}
}

@RestController
@RequestMapping("/location")
class LocationController {
	private final LocationProperties locProps;

	public LocationController(LocationProperties locProps) {
		this.locProps = locProps;
	}

	@GetMapping
	LocationProperties getLocProps() {
		return locProps;
	}

	@GetMapping("/name")
	String getName() {
		return locProps.getName();
	}

	@GetMapping("/description")
	String getDescription() {
		return locProps.getDescription();
	}

	@GetMapping("/region")
	String getRegion() {
		return locProps.getRegion();
	}
}

@ConfigurationProperties(prefix = "location")
class LocationProperties {
	private String name, description, region;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
}

@Data
class BlackBoxProperties {
	private String propertyId, propertyDescription;
}