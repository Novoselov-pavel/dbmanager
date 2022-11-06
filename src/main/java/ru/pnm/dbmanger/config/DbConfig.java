package ru.pnm.dbmanger.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.pnm.dbmanger.exception.CommandLineArgValidationException;
import ru.pnm.dbmanger.model.commandline.CommandLineArgs;
import ru.pnm.dbmanger.service.commandline.CommandlineArgService;
import ru.pnm.dbmanger.service.commandline.validator.CommandlineArgValidator;
import ru.pnm.dbmanger.service.hikari.config.HikariConfigFactory;
import ru.pnm.dbmanger.service.message.MessageService;

/**
 * Класс конфигурации бина Liquibase
 * @author Новоселов Павел
 */
@Configuration
public class DbConfig {

  @Bean
  public HikariDataSource dataSource(@Autowired CommandlineArgService commandlineArgService,
                                     @Autowired CommandlineArgValidator validator,
                                     @Autowired HikariConfigFactory hikariConfigFactory,
                                     @Autowired MessageService messageService){
    CommandLineArgs args = commandlineArgService.getCommandLineArg();
    try {
      validator.validate(args);
    } catch (CommandLineArgValidationException e) {
      messageService.log(e);
      return null;
    }
    return new HikariDataSource(hikariConfigFactory.getConfig(commandlineArgService.getCommandLineArg()));
  }

}
