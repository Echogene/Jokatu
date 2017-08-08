package jokatu.components.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

/**
 * The main config file for the Jokatu server.
 * @author Steven Weston
 */
@Configuration
@ComponentScan("jokatu.components")
open class MainConfiguration
