package me.oussamamessaoudi.openingaccount.infra.controller;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.oussamamessaoudi.openingaccount.application.exception.CodeError;
import me.oussamamessaoudi.openingaccount.application.exception.OpeningAccountException;
import me.oussamamessaoudi.openingaccount.application.exception.ProblemRest;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class ExceptionHandlers {

  public static final String TRACE_ID = "traceId";
  private final MessageSource messageSource;
  private final Tracer tracer;

  @ExceptionHandler(OpeningAccountException.class)
  public ResponseEntity<ProblemRest> handleExceptionOpeningAccount(OpeningAccountException ex) {
    var httpStatus = HttpStatusCode.valueOf(ex.getHttpStatus());
    String codeErreur = ex.getCodeError().getCode();
    return new ResponseEntity<>(
        ProblemRest.builder()
            .codeError(codeErreur)
            .messageError(
                messageSource.getMessage(codeErreur, ex.getParams().toArray(), Locale.getDefault()))
            .properties(getTraceId())
            .build(),
        httpStatus);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ProblemRest> handleException(Exception ex) {
    var codeErreur = CodeError.INTERNAL_ERROR.getCode();
    log.error(codeErreur, ex);
    return new ResponseEntity<>(
        ProblemRest.builder()
            .codeError(codeErreur)
            .messageError(messageSource.getMessage(codeErreur, null, Locale.getDefault()))
            .properties(getTraceId())
            .build(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private Map<String, String> getTraceId() {
    return Map.of(
        TRACE_ID,
        Optional.of(tracer)
            .map(Tracer::currentSpan)
            .map(Span::context)
            .map(TraceContext::traceId)
            .orElse(""));
  }
}
