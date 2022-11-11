package ru.npn.dbmanger.service.url;

import ru.npn.dbmanger.model.commandline.CommandLineArgs;

/**
 * Собирает url для подключения в зависимости от типа базы данных
 *
 * @author Новоселов Павел
 */
public interface UrlBuilder {
  /**
   * Возвращает URL в зависимости от типа базы данных.
   * По дефолту возвращает args.dbUrl() + args.dbName()
   * @param args аргументы командной строки
   * @return url для подключения базы данных
   */
  String getUrl(CommandLineArgs args);
}
