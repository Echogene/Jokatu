package jokatu.test;

import jokatu.components.controllers.Jokatu;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@TestPropertySource(locations = "classpath:test-application.properties")
@SpringBootTest(classes = {Jokatu.class, TestConfig.class})
public @interface JokatuTest {
}
