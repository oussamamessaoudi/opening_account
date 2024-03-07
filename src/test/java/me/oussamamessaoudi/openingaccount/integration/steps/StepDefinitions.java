package me.oussamamessaoudi.openingaccount.integration.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import java.math.BigDecimal;
import java.util.Objects;
import me.oussamamessaoudi.openingaccount.application.domain.model.NewAccountCreatedDTO;
import me.oussamamessaoudi.openingaccount.application.domain.model.NewAccountCreationDTO;
import me.oussamamessaoudi.openingaccount.application.exception.ProblemRest;
import me.oussamamessaoudi.openingaccount.integration.CucumberSpringContextConfig;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CucumberContextConfiguration
public class StepDefinitions extends CucumberSpringContextConfig {

  @LocalServerPort int port;

  @Autowired private TestRestTemplate restTemplate;

  ResponseEntity<NewAccountCreatedDTO> responseEntity;
  ResponseEntity<ProblemRest> responseErrorEntity;

  NewAccountCreationDTO newAccountCreationDTO;

  @Given("create new account for customer {} with deposit {}")
  public void createNewAccountForCustomerWithDepositDeposit(long id, BigDecimal deposit) {
    newAccountCreationDTO =
        NewAccountCreationDTO.builder().customerId(id).initialCredit(deposit).build();
  }

  @When("call the creation api")
  public void callTheCreationApi() {
    responseEntity =
        restTemplate.postForEntity(
            "http://localhost:" + port + "/api/create-account",
            newAccountCreationDTO,
            NewAccountCreatedDTO.class);
  }

  @When("call the creation expecting error")
  public void callTheCreationApiExpectError() {
    responseErrorEntity =
        restTemplate.postForEntity(
            "http://localhost:" + port + "/api/create-account",
            newAccountCreationDTO,
            ProblemRest.class);
  }

  @Then("the account created with customer {} and deposit {}")
  public void theAccountCreatedWithCustomerCustomerAndDepositBalance(
      long customerId, BigDecimal balance) {
    Assertions.assertAll(
        () -> Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode()),
        () ->
            Assertions.assertEquals(
                customerId, Objects.requireNonNull(responseEntity.getBody()).getCustomerId()),
        () ->
            Assertions.assertEquals(
                0,
                balance.compareTo(Objects.requireNonNull(responseEntity.getBody()).getBalance())));
  }

  @Then("the response has error {} and with code message {}")
  public void theResponseHasErrorHttpAndWithCodeMessageCodeMessage(int http, String errorCode) {
    Assertions.assertAll(
        () -> Assertions.assertEquals(http, responseErrorEntity.getStatusCode().value()),
        () ->
            Assertions.assertEquals(
                errorCode, Objects.requireNonNull(responseErrorEntity.getBody()).getCodeError()));
  }
}
