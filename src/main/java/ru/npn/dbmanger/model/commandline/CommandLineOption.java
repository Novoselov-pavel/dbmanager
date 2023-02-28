package ru.npn.dbmanger.model.commandline;

import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Опции командной строки
 *
 * @author Новоселов Павел
 */
public enum CommandLineOption {
  DB_URL("url", "option.url"),
  DB_USERNAME("userName", "option.userName"),
  DB_USER_PASSWORD("userPassword", "option.userPassword"),
  ADMIN_USERNAME("admin", "option.admin"),
  ADMIN_PASSWORD("adminPassword", "option.adminPassword"),
  DB_NAME("database", "option.database"),
  DB_SCHEMA("schema", "option.schema"),
  CHANGELOG_PATH("changelog", "option.changelog");

  private final String optionName;
  private final String descriptionKey;

  CommandLineOption(@NonNull String optionName, @NonNull String descriptionKey) {
    this.optionName = optionName;
    this.descriptionKey = descriptionKey;
  }

  private static final Map<String, CommandLineOption> LOW_CASE_OPTION_MAP = new HashMap<>();

  static {
    for (CommandLineOption value : CommandLineOption.values()) {
      LOW_CASE_OPTION_MAP.put(value.getOptionName().toLowerCase(), value);
    }
  }


  /**
   * Возвращает опции по аргументу командной строки или null
   *
   * @param arg аргумент командной строки
   * @return CommandLineOption или null
   */
  public static CommandLineOption getOption(final String arg) {
    if (arg == null) {
      return null;
    }
    return LOW_CASE_OPTION_MAP.get(arg.toLowerCase());
  }

  public String getOptionName() {
    return optionName;
  }

  public String getOption() {
    return "--" + optionName;
  }

  public String getDescriptionKey() {
    return descriptionKey;
  }
}
