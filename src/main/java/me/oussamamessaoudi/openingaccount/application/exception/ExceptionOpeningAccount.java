package me.oussamamessaoudi.openingaccount.application.exception;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Status;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import javax.net.ssl.SSLEngineResult;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Getter
public class ExceptionOpeningAccount extends RuntimeException {
    private final CodeError codeError;
    private final int httpStatus;
    private Map<String, String> params ;

    @Builder
    public ExceptionOpeningAccount(String message, Throwable cause, CodeError codeError, int httpStatus) {
        super(message, cause);
        this.codeError = codeError;
        this.httpStatus = Optional.of(httpStatus).orElse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }



    public ExceptionOpeningAccount addParam(String key, String value) {
        if(Objects.isNull(this.params)) {
            this.params = new HashMap<>();
        }
        this.params.put(key, value);
        return this;
    }

}

