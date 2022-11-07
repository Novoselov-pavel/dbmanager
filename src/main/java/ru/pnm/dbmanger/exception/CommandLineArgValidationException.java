package ru.pnm.dbmanger.exception;

import lombok.Getter;

import java.util.List;
import java.util.Objects;

/**
 * Исключение при проверке параметров командной строки
 * @author Новоселов Павел
 */
@Getter
public class CommandLineArgValidationException extends Exception {
  private final List<String> exceptionReasons;

  public CommandLineArgValidationException(List<String> exceptionReasons) {
    super(getMessageStringForReasons(exceptionReasons));
    this.exceptionReasons = exceptionReasons;
  }

  private static String getMessageStringForReasons(List<String> exceptionReasons){
    if(Objects.isNull(exceptionReasons)){
      return null;
    }
   StringBuilder builder = new StringBuilder();

    for (String exceptionReason : exceptionReasons) {
      if(Objects.nonNull(exceptionReason)){
        builder.append(exceptionReason).append(System.lineSeparator());
      }
    }

    return builder.toString();
  }

  public String getMessageString(){
    return getMessageStringForReasons(exceptionReasons);
  }
}
