Feature: Detail Customer

  Scenario Outline: Get detail customer
    Given create new accounts for customer <customer> with deposits <deposits>
    When call the creation api
    And call get detail customer <customer>
    Then the detail returned has <numberOfAccount> account and <numberOfTransaction> transaction with a sum amounts of <totalAmount>

    Examples:
      | customer | deposits       | numberOfAccount | numberOfTransaction | totalAmount |
      | 1        | 100, 200, 300  | 3               | 3                   | 600         |
      | 1        | 0              | 1               | 0                   | 0           |
      | 1        | -200           | 1               | 0                   | 0           |

  Scenario Outline: Get detail customer throw error
    Given call get detail customer <customer>
    Then the response has errors <http> and with codes messages <codeMessage>

    Examples:
      | customer | http | codeMessage                                         |
      | 2        | 404  | me.oussamamessaoudi.openingAccount.customerNotFound |
      | 3        | 404  | me.oussamamessaoudi.openingAccount.customerNotFound |
      | 444      | 404  | me.oussamamessaoudi.openingAccount.customerNotFound |
