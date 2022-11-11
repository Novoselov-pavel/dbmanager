package ru.npn.dbmanger.service.hikari.config;

import com.zaxxer.hikari.HikariDataSource;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;

/**
 * Создание HikariDatasource для Liquibase
 * @author Новоселов Павел
 */
public interface HikariDatasourceLiquibaseBuilder {
  HikariDataSource build(CommandLineArgs args,
                         HikariConfigFactory hikariConfigFactory);
}
