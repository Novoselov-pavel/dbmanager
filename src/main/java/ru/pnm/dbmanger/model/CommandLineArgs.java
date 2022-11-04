package ru.pnm.dbmanger.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Аргументы подключения для командной строки
 * @author Новоселов Павел
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CommandLineArgs {
  /*
   * url базы данных для подключения
   * формат jdbc:postgresql://localhost:5432
   */
  private String dbUrl;

  /*
   * Имя администратора базы
   */
  private String adminUserName;

  /**
   * Возвращает пароль администратора базы
   */
  private String adminPassword;

  /**
   * Возвращает имя создаваемого пользователя базы
   */
  private String getDbUserName;

  /**
   * Возвращает пароль создаваемого пользователя базы
   */
  private String getDbUserPassword;

  /**
   * Возвращает имя базы данных
   */
  private String dbName;

  /**
   * Схема базы данных
   */
  private String schema;

  /**
   * Дополнительные свойства для присоединения к базе
   */
  private Map<String, String> additionalProperties;
}
