package ru.pnm.dbmanger.service.operation;

import ru.pnm.dbmanger.model.commandline.CommandLineArgs;
import ru.pnm.dbmanger.model.commandline.CommandLineOperation;
import ru.pnm.dbmanger.model.commandline.DatabaseType;

/**
 * Служба обработки операций
 *
 * @author Новоселов Павел
 */
public interface OperationService {
  /**
   * Служба выполнения операций
   *
   * @param operation операция
   * @param args аргументы командной строки
   */
  void process(CommandLineOperation operation, DatabaseType databaseType, CommandLineArgs args);
}
