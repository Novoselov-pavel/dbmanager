package ru.npn.dbmanger.model.commandline;

import lombok.NonNull;

import java.util.*;

/**
 * Перечисление возможных операций базы данных
 *
 * @author Новоселов Павел
 */
public enum CommandLineOperation {

  HELP("h", "help", 0, false, true, "operation.help"),
  DROP_SCHEMA("ds", "dropSchema", 1, true, false, "operation.dropSchema"),
  DROP_DB("db", "dropDb", 2, true, false, "operation.dropDb"),
  CREATE_DB("cd", "createDb", 3, true, false, "operation.createDb"),
  CREATE_SCHEMA("cs", "createSchema", 4, true, false, "operation.createSchema"),
  UPDATE("u", "update", 5, false, false, "operation.update");

  private final String shortOperationName;

  private final String fullOperationName;

  private final int order;
  private final boolean isCommonOperation;
  private final boolean isHelpOperation;
  private final String descriptionKey;

  /**
   * Описывает доступные операции для выполнения
   *
   *  @param shortOperationName сокращение операции
   * @param fullOperationName полное наименование операции
   * @param order порядок выполнения операции
   * @param isCommonOperation определяет, является ли операция общей операцией над базой данных
   * @param isHelpOperation определяет, является ли операция вызовом справки
   * @param descriptionKey ключ описания операции
   **/
  CommandLineOperation(@NonNull final String shortOperationName,
                       @NonNull final String fullOperationName,
                       int order,
                       boolean isCommonOperation,
                       boolean isHelpOperation,
                       @NonNull String descriptionKey) {
    this.shortOperationName = shortOperationName;
    this.fullOperationName = fullOperationName;
    this.order = order;
    this.isCommonOperation = isCommonOperation;
    this.isHelpOperation = isHelpOperation;
    this.descriptionKey = descriptionKey;
  }

  private static final Map<String, CommandLineOperation> OPERATION_LOW_CASE_MAP = new HashMap<>();
  static {
    for (CommandLineOperation value : CommandLineOperation.values()) {
      OPERATION_LOW_CASE_MAP.put(value.getShortOperationName().toLowerCase(), value);
      OPERATION_LOW_CASE_MAP.put(value.getFullOperationName().toLowerCase(), value);
    }
  }




  /**
   * Возвращает операцию по аргументу командной строки или null
   *
   * @param arg аргумент командной строки
   * @return CommandLineOperation или null
   */
  public static CommandLineOperation getOperation(final String arg) {
    if (arg == null) {
      return null;
    }
    return OPERATION_LOW_CASE_MAP.get(arg.toLowerCase());
  }

  public String getShortOperationName() {
    return shortOperationName;
  }

  public String getFullOperationName() {
    return fullOperationName;
  }

  public String getDescriptionKey() {
    return descriptionKey;
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

  public boolean isHelpOperation() {
    return isHelpOperation;
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
