package ru.npn.dbmanger.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import ru.npn.dbmanger.exception.CommandLineArgValidationException;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;
import ru.npn.dbmanger.service.commandline.validator.CommandlineArgValidator;
import ru.npn.dbmanger.service.hikari.config.HikariConfigFactory;
import ru.npn.dbmanger.service.operation.CommonDatabaseOperationServiceImpl;
import ru.npn.dbmanger.service.commandline.CommandlineArgService;
import ru.npn.dbmanger.service.message.MessageService;
import ru.npn.dbmanger.service.operation.CommonDatabaseOperationService;
import ru.npn.dbmanger.service.operation.CommonOperationProvider;

import java.util.List;

/**
 * Класс конфигурации бина Liquibase
 *
 * @author Новоселов Павел
 */
@Configuration
public class DbConfig {
  private static final String LOG_SETTING = "start.with.setting";

  @Bean("commandLineArgs")
  public CommandLineArgs args(@Autowired CommandlineArgService commandlineArgService) {
    return commandlineArgService.getCommandLineArg();
  }

  @Bean("dataSourceForCommonOperation")
  public HikariDataSource dataSourceForCommonOperation(@Autowired CommandLineArgs args,
                                                       @Autowired CommandlineArgValidator validator,
                                                       @Autowired HikariConfigFactory hikariConfigFactory,
                                                       @Autowired MessageService messageService) {
    try {
      validator.validate(args);
      messageService.logInfo(LOG_SETTING, new String[]{args.toString()});
    } catch (CommandLineArgValidationException e) {
      messageService.log(e);
      return null;
    }
    return new HikariDataSource(hikariConfigFactory.getConfigForDatabaseForCommonOperation(args));
  }

  @Bean("commonDatabaseOperationService")
  public CommonDatabaseOperationService commonDatabaseOperationService(@Autowired @Qualifier("dataSourceForCommonOperation") HikariDataSource hikariDataSource,
                                                                       @Autowired List<CommonOperationProvider> operations,
                                                                       @Autowired MessageService messageService,
                                                                       @Autowired CommandLineArgs args) {
    return new CommonDatabaseOperationServiceImpl(hikariDataSource, operations, messageService, args);
  }


  @Bean
  @DependsOn({"commandLineArgs", "dataSourceForCommonOperation", "commonDatabaseOperationService"})
  @Primary
  public HikariDataSource dataSourceForLiquibase(@Autowired CommandLineArgs args,
                                                 @Autowired HikariConfigFactory hikariConfigFactory) {
    return new HikariDataSource(hikariConfigFactory.getConfigForLiquibase(args));
  }

}
