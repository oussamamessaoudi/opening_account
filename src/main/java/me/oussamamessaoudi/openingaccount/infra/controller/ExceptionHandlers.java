package me.oussamamessaoudi.openingaccount.infra.controller;

import me.oussamamessaoudi.openingaccount.application.exception.ExceptionOpeningAccount;
import me.oussamamessaoudi.openingaccount.application.exception.ProblemRest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(ExceptionOpeningAccount.class)
    public ResponseEntity<ProblemRest> handleExceptionOpeningAccount(ExceptionOpeningAccount ex) {
        var httpStatus = HttpStatusCode.valueOf(ex.getHttpStatus());
        return new ResponseEntity<>(ProblemRest.builder()
                .codeError(ex.getCodeError().getCode())
                .build(), httpStatus);
    }
}
