package ru.npn.dbmanger.model.commandline;

import java.util.Objects;

/**
 * Создержить списко поддерживаемых баз данных
 *
 * @author Новоселов Павел
 */
public enum DatabaseType {
  POSTGRES("postgres","^jdbc:postgresql://\\w+:\\d+/$", "jdbc:postgresql://localhost:5432/");

  private final String databaseTypeName;
  private final String databaseUrlRegex;
  private final String getDatabaseUrlSimple;

  DatabaseType(String databaseTypeName, String databaseUrlRegex, String getDatabaseUrlSimple) {
    this.databaseTypeName = databaseTypeName;
    this.databaseUrlRegex = databaseUrlRegex;
    this.getDatabaseUrlSimple = getDatabaseUrlSimple;
  }

  public String getDatabaseTypeName() {
    return databaseTypeName;
  }

  public String getDatabaseUrlRegex() {
    return databaseUrlRegex;
  }

  public String getGetDatabaseUrlSimple() {
    return getDatabaseUrlSimple;
  }

  public static DatabaseType getTypeFromDbUrl(String dbUrl){
    if(Objects.isNull(dbUrl)){
      return null;
    }

    for (DatabaseType type : DatabaseType.values()) {
      if(dbUrl.matches(type.databaseUrlRegex)){
        return type;
      }
    }
    return null;
  }



}
