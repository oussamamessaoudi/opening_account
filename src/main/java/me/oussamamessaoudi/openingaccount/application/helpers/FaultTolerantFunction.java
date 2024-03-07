package me.oussamamessaoudi.openingaccount.application.helpers;

import java.util.function.Function;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FaultTolerantFunction<T, R> implements Function<T, R> {
  private final Function<T, R> function;
  private final Function<T, R> fallbackFunction;

  public static <T, R> FaultTolerantFunction<T, R> of(Function<T, R> function) {
    return new FaultTolerantFunction<>(function, t -> null);
  }

  public static <T, R> FaultTolerantFunction<T, R> of(
      Function<T, R> function, Function<T, R> fallbackFunction) {
    return new FaultTolerantFunction<>(function, fallbackFunction);
  }

  public R apply(T t) {
    try {
      return function.apply(t);
    } catch (Exception exception) {
      log.error("Tolerate exception", exception);
      return fallbackFunction.apply(t);
    }
  }
}
