Feature: Create Account

  Scenario Outline: Create new Account with a deposit
    Given create new accounts for customer <customer> with deposits <deposit>
    When call the creation api
    Then the account created with customer <customer> and deposits <balance>

    Examples:
      | customer | deposit | balance |
      | 1        | 100     | 100     |
      | 1        | 0       | 0       |
      | 1        | -200    | 0       |

  Scenario Outline: Create new Account throw error
    Given create new accounts for customer <customer> with deposits <deposit>
    When call the creation api
    Then the response has errors <http> and with codes messages <codeMessage>

    Examples:
      | customer | deposit | http | codeMessage                                         |
      | 2        | 100     | 404  | me.oussamamessaoudi.openingAccount.customerNotFound |
      | 3        | 0       | 404  | me.oussamamessaoudi.openingAccount.customerNotFound |
      | 444      | -200    | 404  | me.oussamamessaoudi.openingAccount.customerNotFound |
