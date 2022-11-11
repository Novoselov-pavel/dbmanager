package ru.npn.dbmanger.service;

/**
 * Служба обработки всех операций над базой данных
 *
 * @author Новоселов Павел
 */
public interface DbProcessService {
  /**
   * Запускает выполнение всех операций над базой данных
   * @return true если выполнилось успешно, иначе false
   */
  boolean process();
}
