package me.oussamamessaoudi.openingaccount.application.exception;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemRest {
  private String codeError;
  private String messageError;
  private Map<String, String> properties;
}
