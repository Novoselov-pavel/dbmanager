package ru.npn.dbmanger.service.operation.postgres;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;
import ru.npn.dbmanger.model.commandline.CommandLineOperation;
import ru.npn.dbmanger.model.commandline.DatabaseType;
import ru.npn.dbmanger.model.operation.SqlExpression;
import ru.npn.dbmanger.service.operation.CommonOperationProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Новоселов Павел
 */
@Service
public class PostgresCreateSchemeOperationProvider implements CommonOperationProvider {
  private static final String DEFAULT_SCHEMA = "public";
  private static final String CREATE_USER_MESSAGE_CODE = "db.common.operation.create.user";
  private static final String CREATE_DB_SHEMA_MESSAGE_CODE = "db.common.operation.create.dbschema";
  private static final String GRAN_PRIVILEGE_MESSAGE_CODE = "db.common.operation.create.db.grant.privilege";

  private static final String CREATE_DB_USER = " Do $$ " +
      "begin " +
      "if not exists (select * from pg_catalog.pg_roles where rolname = '%s') " +
      "then " +
      "CREATE ROLE %s WITH LOGIN NOSUPERUSER INHERIT NOCREATEDB " +
      "      NOCREATEROLE NOREPLICATION PASSWORD '%s' ; " +
      "end if; " +
      "end " +
      "$$";
  private static final String GRANT_PRIVILEGE_TO_USER = "GRANT ALL ON DATABASE %s TO %s WITH GRANT OPTION;";
  private static final String CREATE_SCHEMA_IF_NOT_EXIST = "CREATE SCHEMA IF NOT EXISTS %s AUTHORIZATION %s;";


  @Override
  public @NonNull CommandLineOperation getOperation() {
    return CommandLineOperation.CREATE_SCHEMA;
  }

  @Override
  public @NonNull DatabaseType getDatabaseType() {
    return DatabaseType.POSTGRES;
  }

  @Override
  public List<SqlExpression> getExpressions(CommandLineArgs args) {

    List<SqlExpression> retVal = new ArrayList<>(3);
    final SqlExpression createUser = new SqlExpression(String.format(CREATE_DB_USER, args.dbUserName(), args.dbUserName(), args.dbUserPassword()),
        CREATE_USER_MESSAGE_CODE,
        null);
    final SqlExpression grantPrivilege = new SqlExpression(String.format(GRANT_PRIVILEGE_TO_USER, args.dbName(), args.dbUserName()),
        GRAN_PRIVILEGE_MESSAGE_CODE,
        null);
    final String schema = args.schema() == null? DEFAULT_SCHEMA : args.schema();
    final SqlExpression createSchema = new SqlExpression(String.format(CREATE_SCHEMA_IF_NOT_EXIST, schema, args.dbUserName()),
        CREATE_DB_SHEMA_MESSAGE_CODE,
        null);

    retVal.add(createUser);
    retVal.add(grantPrivilege);
    retVal.add(createSchema);
    return retVal;
  }


}
