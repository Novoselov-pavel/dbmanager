package ru.pnm.dbmanger.model.commandline;

import lombok.NonNull;

/**
 * Перечисление возможных операций базы данных
 *
 * @author Новоселов Павел
 */
public enum CommandLineOperation {
  DROP("d", "drop"),
  CREATE("c", "create"),
  UPDATE("u", "update");

  private final String shortOperationName;

  private final String fullOperationName;

  CommandLineOperation(@NonNull String shortOperationName,
                       @NonNull String fullOperationName) {
    this.shortOperationName = shortOperationName;
    this.fullOperationName = fullOperationName;
  }

  /**
   * Возвращает операцию по аргументу командной строки или null
   *
   * @param arg аргумент командной строки
   * @return CommandLineOperation или null
   */
  public static CommandLineOperation getOperation(String arg) {
    if (arg == null) {
      return null;
    }
    for (CommandLineOperation value : CommandLineOperation.values()) {
      if (arg.equalsIgnoreCase(value.getFullOperationName()) || arg.equalsIgnoreCase(value.getShortOperationName())) {
        return value;
      }
    }
    return null;

  }

  public String getShortOperationName() {
    return shortOperationName;
  }

  public String getFullOperationName() {
    return fullOperationName;
  }
}
