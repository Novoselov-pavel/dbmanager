package ru.npn.dbmanger.model.commandline;

import lombok.NonNull;

import java.util.Map;
import java.util.Set;

/**
 * Создает запись с данными полученными из командной строки
 *
 * @param operations           операции которые выполняются над базой
 * @param dbUrl                url базы данных для подключения. Формат jdbc:postgresql://localhost:5432
 * @param adminUserName        имя администратора базы
 * @param adminPassword        пароль администратора базы
 * @param dbUserName           имя создаваемого пользователя базы
 * @param dbUserPassword       пароль создаваемого пользователя базы
 * @param dbName               имя базы данных
 * @param schema               схема базы данных
 * @param additionalProperties дополнительные свойства для присоединения к базе
 */
public record CommandLineArgs(@NonNull Set<CommandLineOperation> operations,
                              String dbUrl,
                              String adminUserName,
                              String adminPassword,
                              String dbUserName,
                              String dbUserPassword,
                              String dbName,
                              String schema,
                              @NonNull Map<String, String> additionalProperties) {
  @Override
  public String toString() {
    return "Command Line Args: " + System.lineSeparator() +
        "operations=" + operations + "," + System.lineSeparator() +
        "dbUrl='" + dbUrl + '\'' + "," + System.lineSeparator() +
        "adminUserName='**********'," + System.lineSeparator() +
        "adminPassword='**********'," + System.lineSeparator() +
        "dbUserName='" + dbUserName + '\'' + "," + System.lineSeparator() +
        "dbUserPassword='" + dbUserPassword + '\'' + "," + System.lineSeparator() +
        "dbName='" + dbName + '\'' + "," + System.lineSeparator() +
        "schema='" + schema + '\'' + "," + System.lineSeparator() +
        "additionalProperties=" + additionalProperties;
  }
}
