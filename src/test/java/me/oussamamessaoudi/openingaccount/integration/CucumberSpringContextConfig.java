package me.oussamamessaoudi.openingaccount.integration;

import io.cucumber.spring.CucumberContextConfiguration;
import me.oussamamessaoudi.openingaccount.OpeningAccountApplication;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(loader = SpringBootContextLoader.class)
@SpringBootTest(
    classes = OpeningAccountApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberSpringContextConfig {}
