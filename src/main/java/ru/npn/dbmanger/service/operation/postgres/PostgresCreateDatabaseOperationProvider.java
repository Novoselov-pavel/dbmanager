package ru.npn.dbmanger.service.operation.postgres;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;
import ru.npn.dbmanger.model.commandline.CommandLineOperation;
import ru.npn.dbmanger.model.commandline.DatabaseType;
import ru.npn.dbmanger.model.operation.SqlExpression;
import ru.npn.dbmanger.service.operation.CommonOperationProvider;

import java.util.Collections;
import java.util.List;

/**
 * @author Новоселов Павел
 */
@Service
public class PostgresCreateDatabaseOperationProvider implements CommonOperationProvider {

  @Override
  public @NonNull CommandLineOperation getOperation() {
    return CommandLineOperation.CREATE_DB;
  }

  @Override
  public @NonNull DatabaseType getDatabaseType() {
    return DatabaseType.POSTGRES;
  }

  @Override
  public List<SqlExpression> getExpressions(CommandLineArgs args) {
    //TODO добавить реализацию
    return Collections.emptyList();
  }


}
