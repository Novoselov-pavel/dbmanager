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
  DB_URL("url"),
  DB_USERNAME("userName"),
  DB_USER_PASSWORD("userPassword"),
  ADMIN_USERNAME("admin"),
  ADMIN_PASSWORD("adminPassword"),
  DB_NAME("database"),
  DB_SCHEMA("schema"),
  CHANGELOG_PATH("changelog");

  private final String optionName;

  CommandLineOption(@NonNull String optionName) {
    this.optionName = optionName;
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
}
