package me.oussamamessaoudi.openingaccount.application.exception;

import jakarta.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.*;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ExceptionOpeningAccount extends RuntimeException {
  private final CodeError codeError;
  private final int httpStatus;
  private List<Serializable> params;

  @Builder
  public ExceptionOpeningAccount(
      String message, Throwable cause, CodeError codeError, int httpStatus) {
    super(message, cause);
    this.codeError = codeError;
    this.httpStatus = Optional.of(httpStatus).orElse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
  }

  public ExceptionOpeningAccount addParam(Serializable value) {
    if (Objects.isNull(this.params)) {
      this.params = new LinkedList<>();
    }
    this.params.add(value);
    return this;
  }
}
