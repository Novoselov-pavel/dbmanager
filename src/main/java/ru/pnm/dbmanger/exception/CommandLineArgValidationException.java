package ru.pnm.dbmanger.exception;

import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Исключение при проверке параметров командной строки
 * @author Новоселов Павел
 */
@Getter
public class CommandLineArgValidationException extends Exception {
  private final List<ExceptionReason> exceptionReasons;

  public CommandLineArgValidationException(List<ExceptionReason> exceptionReasons) {
    super();
    this.exceptionReasons = exceptionReasons;
  }


  public static record ExceptionReason (String reason, String[] args) implements Serializable {
    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      ExceptionReason that = (ExceptionReason) o;
      return Objects.equals(reason, that.reason) && Arrays.equals(args, that.args);
    }

    @Override
    public int hashCode() {
      int result = Objects.hash(reason);
      result = 31 * result + Arrays.hashCode(args);
      return result;
    }

    @Override
    public String toString() {
      return "ExceptionReason{" +
          "reason='" + reason + '\'' +
          ", args=" + Arrays.toString(args) +
          '}';
    }
  }
}
