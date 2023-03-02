package ru.npn.dbmanger.model.commandline;

import lombok.NonNull;
import org.springframework.lang.Nullable;

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
 * @param databaseType         тип базы данных
 * @param changelogPath        путь к файлу changelog
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
                              @Nullable DatabaseType databaseType,
                              @Nullable String changelogPath,
                              @NonNull Map<String, String> additionalProperties) {
  public String argumentToPrettyString(){
    return "Command line arguments: " + System.lineSeparator() +
        "operations=" + operations + "," + System.lineSeparator() +
        getOptionPrettyString(CommandLineOption.DB_URL, dbUrl) +
        getOptionPrettyString(CommandLineOption.ADMIN_USERNAME, adminUserName) +
        getOptionPrettyString(CommandLineOption.ADMIN_PASSWORD, adminPassword) +
        getOptionPrettyString(CommandLineOption.DB_USERNAME, dbUserName) +
        getOptionPrettyString(CommandLineOption.DB_USER_PASSWORD, dbUserPassword) +
        getOptionPrettyString(CommandLineOption.DB_NAME, dbUserPassword) +
        getOptionPrettyString(CommandLineOption.DB_SCHEMA, dbUserPassword) +
        getOptionPrettyString(CommandLineOption.CHANGELOG_PATH, changelogPath) +
        getOptionPrettyString(CommandLineOption.CHANGELOG_PATH, changelogPath);
  }

  public String getOptionPrettyString(CommandLineOption option, String value){
    return option.getOptionName() + "=" + value + "," + System.lineSeparator();
  }
}
