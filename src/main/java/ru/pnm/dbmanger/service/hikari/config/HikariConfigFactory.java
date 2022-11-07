package ru.pnm.dbmanger.service.hikari.config;

import com.zaxxer.hikari.HikariConfig;
import ru.pnm.dbmanger.model.commandline.CommandLineArgs;

/**
 * Фабрика для получения
 * @author Новоселов Павел
 */
public interface HikariConfigFactory {
  /**
   * Создает конфигурацию для подключения к базе данных по настройкам
   * для пролития скриптов с помощью liquibase
   *
   * @param args аргументы командной строки
   * @return HikariConfig
   */
  HikariConfig getConfigForLiquibase(CommandLineArgs args);

  /**
   * Создает конфигурацию для подключения к базе данных по настройкам
   * для выполнения общих операций над базой данных
   *
   * @param args аргументы командной строки
   * @return HikariConfig
   */
  HikariConfig getConfigForDatabaseForCommonOperation(CommandLineArgs args);

}
