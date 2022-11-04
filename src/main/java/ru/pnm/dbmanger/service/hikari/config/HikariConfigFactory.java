package ru.pnm.dbmanger.service.hikari.config;

import com.zaxxer.hikari.HikariConfig;
import ru.pnm.dbmanger.model.CommandLineArgs;

/**
 * Фабрика для получения
 * @author Новоселов Павел
 */
public interface HikariConfigFactory {
  /**
   * Создает конфигурацию для подключения к базе данных по настройкам
   *
   * @param args аргументы командной строки
   * @return HikariConfig
   */
  HikariConfig getConfig(CommandLineArgs args);
}
