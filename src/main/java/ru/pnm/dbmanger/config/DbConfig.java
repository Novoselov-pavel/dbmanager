package ru.pnm.dbmanger.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import ru.pnm.dbmanger.exception.CommandLineArgValidationException;
import ru.pnm.dbmanger.model.commandline.CommandLineArgs;
import ru.pnm.dbmanger.service.commandline.CommandlineArgService;
import ru.pnm.dbmanger.service.commandline.validator.CommandlineArgValidator;
import ru.pnm.dbmanger.service.hikari.config.HikariConfigFactory;
import ru.pnm.dbmanger.service.message.MessageService;
import ru.pnm.dbmanger.service.operation.CommonDatabaseOperationService;
import ru.pnm.dbmanger.service.operation.CommonDatabaseOperationServiceImpl;

/**
 * Класс конфигурации бина Liquibase
 *
 * @author Новоселов Павел
 */
@Configuration
public class DbConfig {

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
    } catch (CommandLineArgValidationException e) {
      messageService.log(e);
      return null;
    }
    return new HikariDataSource(hikariConfigFactory.getConfigForDatabaseForCommonOperation(args));
  }

  @Bean("commonDatabaseOperationService")
  public CommonDatabaseOperationService commonDatabaseOperationService(@Autowired @Qualifier("dataSourceForCommonOperation") HikariDataSource hikariDataSource,
                                                                       @Autowired CommandLineArgs args) {
    return new CommonDatabaseOperationServiceImpl(hikariDataSource, args);
  }


  @Bean
  @DependsOn({"commandLineArgs", "dataSourceForCommonOperation", "commonDatabaseOperationService"})
  @Primary
  public HikariDataSource dataSourceForLiquibase(@Autowired CommandLineArgs args,
                                                 @Autowired HikariConfigFactory hikariConfigFactory) {
    return new HikariDataSource(hikariConfigFactory.getConfigForLiquibase(args));
  }

}
