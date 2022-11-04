package ru.pnm.dbmanger.model.commandline;

/**
 * Перечисление возможных операций базы данных
 *
 * @author Новоселов Павел
 */
public enum CommandLineOperation {
  DROP("-d", "--drop"),
  CREATE("-c", "--create"),
  UPDATE("-u", "--update");

  private final String shortOperationName;

  private final String fullOperationName;

  CommandLineOperation(String shortOperationName, String fullOperationName) {
    this.shortOperationName = shortOperationName;
    this.fullOperationName = fullOperationName;
  }



}
