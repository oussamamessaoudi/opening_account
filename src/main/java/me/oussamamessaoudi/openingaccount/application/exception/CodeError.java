package me.oussamamessaoudi.openingaccount.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeError {
  INTERNAL_ERROR("me.oussamamessaoudi.openingAccount.internalError"),
  CUSTOMER_NOT_FOUND("me.oussamamessaoudi.openingAccount.customerNotFound"),
  CUSTOMER_SERVICE_UNAVAILABLE("me.oussamamessaoudi.openingAccount.customerServiceUnavailable"),
  ERROR_CREATING_ACCOUNT("me.oussamamessaoudi.openingAccount.errorCreatingAccount"),
  ERROR_CREATING_TRANSACTION("me.oussamamessaoudi.openingAccount.errorCreatingTransaction"),
  TRANSACTION_AMOUNT_INVALID("me.oussamamessaoudi.openingAccount.transactionAmountInvalid");

  private final String code;
}
