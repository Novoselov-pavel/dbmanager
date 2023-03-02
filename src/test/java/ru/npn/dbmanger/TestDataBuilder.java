package ru.npn.dbmanger;

import ru.npn.dbmanger.model.commandline.CommandLineArgs;
import ru.npn.dbmanger.model.commandline.CommandLineOperation;
import ru.npn.dbmanger.model.commandline.DatabaseType;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
/**
 * данные для теста
 *
 * @author Новоселов Павел
 */
public class TestDataBuilder {

  public static CommandLineArgs argsWithoutCommonOperation(){
    Set<CommandLineOperation> operations = new HashSet<>();
    operations.add(CommandLineOperation.UPDATE);
    String dbUrl = DatabaseType.POSTGRES.getGetDatabaseUrlSimple();
    String adminUserName = "admin";
    String adminPassword = "admin";
    String dbUserName = "user";
    String dbUserPassword = "user";
    String dbName = "dbname-test-absent-db-rsdfh";
    String schema = "dnschema";
    return new CommandLineArgs(operations,
        dbUrl,
        adminUserName,
        adminPassword,
        dbUserName,
        dbUserPassword,
        dbName,
        schema,
        DatabaseType.POSTGRES,
        null,
        Collections.emptyMap());
  }


  public static CommandLineArgs argsWithCreateDbSchemaCommonOperation(){
    Set<CommandLineOperation> operations = new HashSet<>();
    operations.add(CommandLineOperation.CREATE_SCHEMA);
    String dbUrl = DatabaseType.POSTGRES.getGetDatabaseUrlSimple();
    String adminUserName = "admin";
    String adminPassword = "admin";
    String dbUserName = "user";
    String dbUserPassword = "user";
    String dbName = "dbname-test-absent-db-rsdfh";
    String schema = "dnschema";
    return new CommandLineArgs(operations,
        dbUrl,
        adminUserName,
        adminPassword,
        dbUserName,
        dbUserPassword,
        dbName,
        schema,
        DatabaseType.POSTGRES,
        null,
        Collections.emptyMap());
  }
}
