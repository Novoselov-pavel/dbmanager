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
public class PostgresDropSchemeOperationProvider implements CommonOperationProvider {
  private static final String DEFAULT_SCHEMA = "public";
  private static final String DROP_SCHEME_MESSAGE_CODE = "db.common.operation.drop.dbschema";
  private static final String DROP_SCHEMA_IF_NOT_EXIST = "DROP SCHEMA %s CASCADE;";

  @Override
  public @NonNull CommandLineOperation getOperation() {
    return CommandLineOperation.DROP_SCHEMA;
  }

  @Override
  public @NonNull DatabaseType getDatabaseType() {
    return DatabaseType.POSTGRES;
  }

  @Override
  public List<SqlExpression> getExpressions(CommandLineArgs args) {
    List<SqlExpression> retVal = new ArrayList<>(1);
    final String schema = args.schema() == null? DEFAULT_SCHEMA : args.schema();
    final SqlExpression dropSchema = new SqlExpression(String.format(DROP_SCHEMA_IF_NOT_EXIST, schema),
        DROP_SCHEME_MESSAGE_CODE,
        new String[] {schema});
    retVal.add(dropSchema);
    return retVal;
  }
}
