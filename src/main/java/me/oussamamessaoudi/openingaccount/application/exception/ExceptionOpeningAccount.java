package me.oussamamessaoudi.openingaccount.application.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
public class ExceptionOpeningAccount extends RuntimeException {
    private final CodeError codeError;
    private final HttpStatus httpStatus;
    private Map<String, String> params;

    @Builder
    public ExceptionOpeningAccount(String message, Throwable cause, CodeError codeError, HttpStatus httpStatus) {
        super(message, cause);
        this.codeError = codeError;
        this.httpStatus = httpStatus;
    }

    public ExceptionOpeningAccount addParam(String key, String value) {
        if(Objects.isNull(this.params)) {
            this.params = new HashMap<>();
        }
        this.params.put(key, value);
        return this;
    }

}
