package ru.npn.dbmanger.service.operation.liquibase;

import lombok.NonNull;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;
import ru.npn.dbmanger.model.commandline.CommandLineOperation;
import ru.npn.dbmanger.model.commandline.DatabaseType;

import javax.sql.DataSource;

/**
 * @author Новоселов Павел
 */
public interface LiquibaseOperationProvider {
  /**
   * Операция выполняемая имплементацией
   *
   * @return CommandLineOperation
   */
  @NonNull
  CommandLineOperation getOperation();

  /**
   * Проверяет, поддерживается ли указанный тип базы данных
   *
   * @return true если поддерживается
   */
  boolean isDatabaseTypeSupported(DatabaseType databaseType);

  /**
   * Запускает обработку операции исходя из аргументов командной строки и источника подключения
   *
   * @param dataSource источник подключения
   * @param args аргументы командной строки
   */
  void process(DataSource dataSource, CommandLineArgs args);

}
