package ru.pnm.dbmanger.model.commandline;

import lombok.NonNull;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * Перечисление возможных операций базы данных
 *
 * @author Новоселов Павел
 */
public enum CommandLineOperation {

  DROP_SCHEMA("ds", "dropSchema", 1, true),
  DB_DROP("db", "dropDb", 2, true),
  CREATE("c", "create", 3, true),
  UPDATE("u", "update", 4, false);

  private final String shortOperationName;

  private final String fullOperationName;

  private final int order;
  private final boolean isCommonOperation;

  CommandLineOperation(@NonNull String shortOperationName,
                       @NonNull String fullOperationName,
                       int order, boolean isCommonOperation) {
    this.shortOperationName = shortOperationName;
    this.fullOperationName = fullOperationName;
    this.order = order;
    this.isCommonOperation = isCommonOperation;
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

  public static List<CommandLineOperation> getListByOrder() {
    return Arrays.stream(CommandLineOperation.values()).sorted(Comparator.naturalOrder()).toList();
  }

  public int getOrder() {
    return order;
  }

  public boolean isCommonOperation() {
    return isCommonOperation;
  }

  public static boolean hasCommonDatabaseOperation(@NonNull Set<CommandLineOperation> operations) {
    for (CommandLineOperation operation : operations) {
      if (operation.isCommonOperation) {
        return true;
      }
    }
    return false;
  }
}
