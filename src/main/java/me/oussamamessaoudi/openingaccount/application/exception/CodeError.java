package me.oussamamessaoudi.openingaccount.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeError {
  INTERNAL_ERROR("me.oussamamessaoudi.openingAccount.internalError"),
  CUSTOMER_NOT_FOUND("me.oussamamessaoudi.openingAccount.customerNotFound"),
  ERROR_CREATING_TRANSACTION("me.oussamamessaoudi.openingAccount.errorCreatingTransaction");

  private final String code;
}
