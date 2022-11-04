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
  @Autowired
  private CommandlineArgService commandlineArgService;
  @Autowired
  private HikariConfigFactory hikariConfigFactory;

  @Bean
  public HikariDataSource dataSource(){
    return new HikariDataSource(hikariConfigFactory.getConfig(commandlineArgService.getCommandLinaArg()));
  }

}
