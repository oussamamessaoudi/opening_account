package me.oussamamessaoudi.openingaccount.application.helpers;

import java.util.function.Predicate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FaultTolerantPredicate<T> implements Predicate<T> {
  private final Predicate<T> predicate;
  private final Predicate<T> fallbackPredicate;

  public static <T> FaultTolerantPredicate<T> of(
      Predicate<T> function, Predicate<T> fallbackFunction) {
    return new FaultTolerantPredicate<>(function, fallbackFunction);
  }

  @Override
  public boolean test(T t) {
    try {
      return predicate.test(t);
    } catch (Exception exception) {
      log.error("Tolerate exception", exception);
      return fallbackPredicate.test(t);
    }
  }
}
