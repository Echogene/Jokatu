package jokatu.components.controllers

import jokatu.components.config.MainConfiguration
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import

/**
 * Run the Jokatu web server.
 * @author Steven Weston
 */
@SpringBootApplication
@Import(MainConfiguration::class)
class Jokatu

fun main(args: Array<String>) {
	SpringApplication.run(Jokatu::class.java, *args)
}
