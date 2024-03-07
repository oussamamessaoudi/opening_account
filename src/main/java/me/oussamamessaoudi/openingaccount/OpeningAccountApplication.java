package me.oussamamessaoudi.openingaccount;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class OpeningAccountApplication {

  public static void main(String[] args) {
    SpringApplication.run(OpeningAccountApplication.class, args);
  }
}
