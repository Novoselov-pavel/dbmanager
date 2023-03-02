package ru.npn.dbmanger.service.db.operation;

/**
 * Служба выполнения общих операций над базой данных (создание базы и схемы, пользователя, удаление базы, схемы)
 *
 * @author Новоселов Павел
 */
public interface CommonDatabaseOperationService {
  /**
   * Выполняет общие операции с базой данных.
   *
   * @return true операции выполнены успешно или не требуются, false - выполнены с ошибкой
   */
  boolean processCommonOperations();
}
