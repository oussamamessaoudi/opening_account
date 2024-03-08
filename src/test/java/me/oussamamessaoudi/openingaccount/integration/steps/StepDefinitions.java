package me.oussamamessaoudi.openingaccount.integration.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import me.oussamamessaoudi.openingaccount.application.domain.model.*;
import me.oussamamessaoudi.openingaccount.application.exception.ProblemRest;
import me.oussamamessaoudi.openingaccount.integration.CucumberSpringContextConfig;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;

@CucumberContextConfiguration
public class StepDefinitions extends CucumberSpringContextConfig {

  @LocalServerPort int port;

  @Autowired private TestRestTemplate restTemplate;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private JdbcTemplate jdbcTemplate;

  @Before
  public void clear() throws SQLException {
    try (Connection connection =
        Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()) {
      ScriptUtils.executeSqlScript(connection, new ClassPathResource("db/data.sql"));
    }
  }

  List<?> requests;
  List<ResponseEntity<String>> responses;

  @Given("create new accounts for customer {} with deposits {}")
  public void createNewAccountForCustomerWithDepositDeposit(long id, String deposits) {
    requests =
        buildList(deposits, BigDecimal::new).stream()
            .map(
                deposit ->
                    NewAccountCreationDTO.builder().customerId(id).initialCredit(deposit).build())
            .toList();
  }

  @When("call the creation api")
  public void callTheCreationApi() {
    responses =
        requests.stream()
            .map(
                request ->
                    restTemplate.postForEntity(
                        "http://localhost:" + port + "/api/account", request, String.class))
            .toList();
  }

  @Then("the account created with customer {} and deposits {}")
  public void theAccountCreatedWithCustomerCustomerAndDepositBalance(
      long customerId, String balancesNotParsed) {
    var balances = buildList(balancesNotParsed, BigDecimal::new);
    for (int i = 0; i < responses.size(); i++) {
      var response = responses.get(i);
      var balance = balances.get(i);
      var mappedResponse = new AtomicReference<NewAccountCreatedDTO>();
      Assertions.assertAll(
          () -> Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode()),
          () ->
              mappedResponse.set(
                  objectMapper.readValue(response.getBody(), NewAccountCreatedDTO.class)),
          () -> Assertions.assertEquals(customerId, mappedResponse.get().getCustomerId()),
          () -> Assertions.assertEquals(0, balance.compareTo(mappedResponse.get().getBalance())));
    }
  }

  @Then("the response has errors {} and with codes messages {}")
  public void theResponseHasErrorHttpAndWithCodeMessageCodeMessage(
      String httpsNotParsed, String errorCodesNotParsed) {
    var https = buildList(httpsNotParsed, Integer::valueOf);
    var errorCodes = buildList(errorCodesNotParsed, String::new);

    for (int i = 0; i < responses.size(); i++) {
      var response = responses.get(i);
      var http = https.get(i);
      var errorCode = errorCodes.get(i);
      var mappedResponse = new AtomicReference<ProblemRest>();
      Assertions.assertAll(
          () -> Assertions.assertEquals(http, response.getStatusCode().value()),
          () -> mappedResponse.set(objectMapper.readValue(response.getBody(), ProblemRest.class)),
          () -> Assertions.assertEquals(errorCode, mappedResponse.get().getCodeError()));
    }
  }

  @And("call get detail customer {}")
  public void getDetailCustomer(String ids) {
    responses =
        buildList(ids, Long::valueOf).stream()
            .map(
                id ->
                    restTemplate.getForEntity(
                        "http://localhost:" + port + "/api/customer?id={id}", String.class, id))
            .toList();
  }

  @Then("the detail returned has {} account and {} transaction with a sum amounts of {}")
  public void theDetailReturnedHasAccountAndTransactionWithAnAmountOfBalance(
      int numberOfAccounts, int numberOfTransaction, BigDecimal totalAmount)
      throws JsonProcessingException {
    for (ResponseEntity<String> response : responses) {
      var customer = objectMapper.readValue(response.getBody(), CustomerDTO.class);
      Assertions.assertAll(
          () -> Assertions.assertEquals(200, response.getStatusCode().value()),
          () -> Assertions.assertEquals(numberOfAccounts, customer.getAccounts().size()),
          () ->
              Assertions.assertEquals(
                  numberOfTransaction,
                  customer.getAccounts().stream()
                      .map(AccountDTO::getTransactions)
                      .flatMap(Collection::stream)
                      .toList()
                      .size()),
          () ->
              Assertions.assertEquals(
                  0,
                  totalAmount.compareTo(
                      customer.getAccounts().stream()
                          .map(AccountDTO::getTransactions)
                          .flatMap(Collection::stream)
                          .map(TransactionDTO::getAmount)
                          .reduce(BigDecimal.ZERO, BigDecimal::add))));
    }
  }

  public static <T> List<T> buildList(String value, Function<String, T> convert) {
    return Arrays.stream(value.split(",\\s*")).map(convert).toList();
  }
}
