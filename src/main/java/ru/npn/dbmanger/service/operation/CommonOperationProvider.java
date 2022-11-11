package ru.npn.dbmanger.service.operation;

import lombok.NonNull;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;
import ru.npn.dbmanger.model.commandline.CommandLineOperation;
import ru.npn.dbmanger.model.commandline.DatabaseType;
import ru.npn.dbmanger.model.operation.SqlExpression;

import java.util.List;

/**
 * Интерфейс общих операций (создание базы и схемы, пользователя, удаление базы, схемы) над базой данных
 *
 * @author Новоселов Павел
 */
public interface CommonOperationProvider {

  /**
   * Операция выполняемая имплементацией
   *
   * @return CommandLineOperation
   */
  @NonNull
  CommandLineOperation getOperation();

  /**
   * Возвращает тип базы данных имплементации
   *
   * @return DatabaseType
   */
  @NonNull
  DatabaseType getDatabaseType();

  /**
   * Возвращает sql для запуска
   * @param args аргументы командной строки
   * @return выражения
   */
  List<SqlExpression> getExpressions(CommandLineArgs args);
}
