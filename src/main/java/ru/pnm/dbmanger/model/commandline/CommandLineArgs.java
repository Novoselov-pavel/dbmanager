package ru.pnm.dbmanger.model.commandline;

import java.util.Map;
import java.util.Set;

/**
 * Создает запись с данными полученными из командной строки
 *
 * @param operations операции которые выполняются над базой
 * @param dbUrl   url базы данных для подключения. Формат jdbc:postgresql://localhost:5432
 * @param adminUserName имя администратора базы
 * @param adminPassword пароль администратора базы
 * @param dbUserName имя создаваемого пользователя базы
 * @param dbUserPassword пароль создаваемого пользователя базы
 * @param dbName имя базы данных
 * @param schema схема базы данных
 * @param additionalProperties дополнительные свойства для присоединения к базе
 */
public record CommandLineArgs (Set<CommandLineOperation> operations,
                               String dbUrl,
                               String adminUserName,
                               String adminPassword,
                               String dbUserName,
                               String dbUserPassword,
                               String dbName,
                               String schema,
                               Map<String, String> additionalProperties) {}
