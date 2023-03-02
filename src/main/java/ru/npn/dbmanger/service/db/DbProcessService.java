package ru.npn.dbmanger.service.db;

import org.springframework.lang.Nullable;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;
import ru.npn.dbmanger.service.db.operation.CommonDatabaseOperationService;

/**
 * Служба обработки всех операций над базой данных
 *
 * @author Новоселов Павел
 */
public interface DbProcessService {
  /**
   * Запускает выполнение всех операций над базой данных
   * @return true если выполнилось успешно, иначе false
   */
  boolean process(CommandLineArgs args, @Nullable CommonDatabaseOperationService commonDatabaseOperationService);
}
