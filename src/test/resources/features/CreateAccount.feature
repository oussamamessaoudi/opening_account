Feature: Create Account

  Scenario Outline: Create new Account with a deposit
    Given create new account for customer <customer> with deposit <deposit>
    When call the creation api
    Then the account created with customer <customer> and deposit <balance>

    Examples:
      | customer | deposit | balance |
      | 1        | 100     | 100     |
      | 1        | 0       | 0       |
      | 1        | -200    | 0       |

  Scenario Outline: Create new Account with a deposit
    Given create new account for customer <customer> with deposit <deposit>
    When call the creation expecting error
    Then the response has error <http> and with code message <codeMessage>

    Examples:
      | customer | deposit | http | codeMessage                                         |
      | 2        | 100     | 404  | me.oussamamessaoudi.openingAccount.customerNotFound |
      | 3        | 0       | 404  | me.oussamamessaoudi.openingAccount.customerNotFound |
      | 444      | -200    | 404  | me.oussamamessaoudi.openingAccount.customerNotFound |
