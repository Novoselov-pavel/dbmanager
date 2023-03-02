package ru.npn.dbmanger.service;

/**
 * Служба запуска операций приложения
 *
 * @author Новоселов Павел
 */
public interface DbManagerService {

  /**
   * Запускает выполнение операций указанных в аргументах командной строки
   *
   */
  void execute();
}
