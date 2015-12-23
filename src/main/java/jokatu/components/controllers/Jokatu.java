package jokatu.components.controllers;

import jokatu.components.config.MainConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;

/**
 * Run the Jokatu web server.
 * @author Steven Weston
 */
@Controller
@EnableAutoConfiguration
@Import(MainConfiguration.class)
public class Jokatu {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Jokatu.class, args);
	}
}
