package me.oussamamessaoudi.openingaccount.application.exception;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ProblemRest {
    private String codeError;
    private String messageError;
    private Map<String, String> properties;
}
