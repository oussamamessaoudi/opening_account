package me.oussamamessaoudi.openingaccount.application.exception;

import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProblemRest {
  private String codeError;
  private String messageError;
  private Map<String, String> properties;
}
