package ru.pnm.dbmanger.service.operation;

/**
 * Служба выполнения общих операций над базой данных (создание базы и схемы, пользователя, удаление базы, схемы)
 *
 * @author Новоселов Павел
 */
public interface CommonDatabaseOperationService {
  void processCommonOperation();
}
