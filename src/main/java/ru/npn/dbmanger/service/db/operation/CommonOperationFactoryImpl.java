package ru.npn.dbmanger.service.db.operation;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;
import ru.npn.dbmanger.service.hikari.config.HikariConfigFactory;
import ru.npn.dbmanger.service.message.MessageService;

import java.util.List;

/**
 * @author Новоселов Павел
 */
@Service
@RequiredArgsConstructor
public class CommonOperationFactoryImpl implements CommonOperationFactory {

  @Override
  public CommonDatabaseOperationService createCommonOperationService(CommandLineArgs args,
                                                                     HikariConfigFactory hikariConfigFactory,
                                                                     List<CommonOperationProvider> operationsProviders,
                                                                     MessageService messageService) {
    HikariDataSource dataSource = new  HikariDataSource(hikariConfigFactory.getConfigForDatabaseForCommonOperation(args));
    return new CommonDatabaseOperationServiceImpl(dataSource, operationsProviders, messageService, args);
  }
}
