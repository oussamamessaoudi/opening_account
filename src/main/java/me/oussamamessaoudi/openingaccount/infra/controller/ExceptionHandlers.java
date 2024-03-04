package me.oussamamessaoudi.openingaccount.infra.controller;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import me.oussamamessaoudi.openingaccount.application.exception.OpeningAccountException;
import me.oussamamessaoudi.openingaccount.application.exception.ProblemRest;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@AllArgsConstructor
public class ExceptionHandlers {

  private final MessageSource messageSource;
  private final Tracer tracer;

  @ExceptionHandler(OpeningAccountException.class)
  public ResponseEntity<ProblemRest> handleExceptionOpeningAccount(OpeningAccountException ex) {
    var httpStatus = HttpStatusCode.valueOf(ex.getHttpStatus());
    String code = ex.getCodeError().getCode();
    return new ResponseEntity<>(
        ProblemRest.builder()
            .codeError(code)
            .messageError(
                messageSource.getMessage(code, ex.getParams().toArray(), Locale.getDefault()))
            .properties(
                Map.of(
                    "traceId",
                    Optional.of(tracer)
                        .map(Tracer::currentSpan)
                        .map(Span::context)
                        .map(TraceContext::traceId)
                        .orElse("")))
            .build(),
        httpStatus);
  }
}
