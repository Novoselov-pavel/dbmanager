package ru.npn.dbmanger.model.commandline;

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
  DB_SCHEMA("schema");

  private final String optionName;

  CommandLineOption(String optionName) {
    this.optionName = optionName;
  }

  /**
   * Возвращает опции по аргументу командной строки или null
   *
   * @param arg аргумент командной строки
   * @return CommandLineOption или null
   */
  public static CommandLineOption getOption(String arg) {
    if (arg == null) {
      return null;
    }
    for (CommandLineOption value : CommandLineOption.values()) {
      if (arg.equalsIgnoreCase(value.getOptionName())) {
        return value;
      }
    }
    return null;

  }

  public String getOptionName() {
    return optionName;
  }

  public String getOption() {
    return "--" + optionName;
  }
}
