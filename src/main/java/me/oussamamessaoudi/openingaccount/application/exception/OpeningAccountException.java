package me.oussamamessaoudi.openingaccount.application.exception;

import jakarta.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.*;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OpeningAccountException extends RuntimeException {
  private final CodeError codeError;
  private final int httpStatus;
  private List<Serializable> params;

  @Builder
  public OpeningAccountException(
      String message, Throwable cause, CodeError codeError, Integer httpStatus) {
    super(message, cause);
    this.codeError = codeError;
    this.httpStatus =
        Optional.ofNullable(httpStatus).orElse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
  }

  public OpeningAccountException addParam(Serializable value) {
    if (Objects.isNull(this.params)) {
      this.params = new LinkedList<>();
    }
    this.params.add(value);
    return this;
  }
}
