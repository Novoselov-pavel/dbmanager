package ru.npn.dbmanger.service.db.operation;

import ru.npn.dbmanger.model.commandline.CommandLineArgs;
import ru.npn.dbmanger.service.hikari.config.HikariConfigFactory;
import ru.npn.dbmanger.service.message.MessageService;

import java.util.List;

/**
 *  Фабрика создания службы работы с общими операциями над базой данных
 *
 * @author Новоселов Павел
 */
public interface CommonOperationFactory {

  /*
   * Создает реализацию CommonDatabaseOperationService, если требуется
   * * @param args аргументы командной строки
   *
   * @return CommonDatabaseOperationService или null
   */


  /**
   *
   * @param args аргументы команднеой строки
   * @param hikariConfigFactory служба создания подключения к базе данных
   * @param operationsProviders бины служб выполняющих операции над базой данных
   * @param messageService служба отправки сообщений
   * @return CommonOperationProvider
   */
  CommonDatabaseOperationService createCommonOperationService(CommandLineArgs args,
                                                              HikariConfigFactory hikariConfigFactory,
                                                              List<CommonOperationProvider> operationsProviders,
                                                              MessageService messageService);

}
