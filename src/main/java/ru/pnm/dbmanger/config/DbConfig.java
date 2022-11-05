package ru.pnm.dbmanger.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.pnm.dbmanger.service.commandline.CommandlineArgService;
import ru.pnm.dbmanger.service.hikari.config.HikariConfigFactory;

/**
 * Класс конфигурации бина Liquibase
 * @author Новоселов Павел
 */
@Configuration
public class DbConfig {

  @Bean
  public HikariDataSource dataSource(@Autowired CommandlineArgService commandlineArgService,
                                     @Autowired HikariConfigFactory hikariConfigFactory){
    return new HikariDataSource(hikariConfigFactory.getConfig(commandlineArgService.getCommandLineArg()));
  }

}
