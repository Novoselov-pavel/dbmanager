package ru.npn.dbmanger.service.operation;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;
import ru.npn.dbmanger.model.commandline.CommandLineOperation;
import ru.npn.dbmanger.model.commandline.DatabaseType;
import ru.npn.dbmanger.model.operation.SqlExpression;
import ru.npn.dbmanger.service.message.MessageService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * @author Новоселов Павел
 */
public class CommonDatabaseOperationServiceImpl implements CommonDatabaseOperationService {
  private final Logger logger = LogManager.getLogger(CommonDatabaseOperationServiceImpl.class);
  private static final String COMMON_OPERATIONS_START = "db.common.operations.start";
  private static final String COMMON_OPERATION_START = "db.common.operation.start";
  private static final String COMMON_OPERATION_END = "db.common.operation.end";
  private static final String COMMON_OPERATION_ERROR = "db.common.operation.error";
  private static final String COMMON_OPERATION_IS_ABSENT = "common.operation.is.absent";


  private final HikariDataSource hikariDataSource;
  private final List<CommonOperationProvider> operations;
  private final MessageService messageService;

  private final CommandLineArgs args;

  public CommonDatabaseOperationServiceImpl(@Qualifier("dataSourceForCommonOperation") HikariDataSource hikariDataSource,
                                            List<CommonOperationProvider> operations,
                                            MessageService messageService,
                                            CommandLineArgs args) {
    this.hikariDataSource = hikariDataSource;
    this.operations = operations;
    this.messageService = messageService;
    this.args = args;
  }

  @Override
  public boolean processCommonOperations() {
    if(Objects.isNull(hikariDataSource)){
      return false;
    }
    if (!CommandLineOperation.hasCommonDatabaseOperation(args.operations())) {
      return true;
    }
    final List<CommandLineOperation> commonOperationByPriority = args.operations().stream()
        .filter(CommandLineOperation::isCommonOperation)
        .sorted(Comparator.naturalOrder())
        .toList();

    final DatabaseType databaseType = DatabaseType.getTypeFromDbUrl(args.dbUrl());
    Assert.notNull(databaseType, "DatabaseType can't be null");

    messageService.logInfo(COMMON_OPERATIONS_START, new String[]{commonOperationByPriority.toString()});
    for (CommandLineOperation commandLineOperation : commonOperationByPriority) {
      messageService.logInfo(COMMON_OPERATION_START, new String[]{commandLineOperation.toString()});
      final CommonOperationProvider commonOperationProvider = getCurrentDatabaseOperationFromList(commandLineOperation, operations, databaseType);
      if (Objects.isNull(commonOperationProvider)) {
        messageService.logError(COMMON_OPERATION_IS_ABSENT, new String[]{commandLineOperation.toString(), databaseType.toString()});
        return false;
      }
      if (!runInTransaction(commonOperationProvider)) {
        messageService.logError(COMMON_OPERATION_ERROR, new String[]{commandLineOperation.toString()});
        return false;
      }
      messageService.logInfo(COMMON_OPERATION_END, new String[]{commandLineOperation.toString()});
    }
    return true;
  }

  private CommonOperationProvider getCurrentDatabaseOperationFromList(final CommandLineOperation operation,
                                                                      final List<CommonOperationProvider> operations,
                                                                      final DatabaseType databaseType) {
    return operations.stream().filter(x -> Objects.equals(operation, x.getOperation())
        && Objects.equals(databaseType, x.getDatabaseType())).findFirst().orElse(null);
  }


  private boolean runInTransaction(final CommonOperationProvider operation) {
    try (Connection connection = hikariDataSource.getConnection()) {
      connection.setAutoCommit(false);
      for (SqlExpression expression : operation.getExpressions(args)) {
        runExpression(connection, expression);
      }
      connection.commit();
    } catch (SQLException e) {
      logger.error(e.getMessage(), e);
      return false;
    }
    return true;
  }


  private void runExpression(Connection connection, SqlExpression expression) throws SQLException {
    try (PreparedStatement statement = connection.prepareStatement(expression.sqlExpression())) {
      statement.execute();
      if(Objects.nonNull(expression.messageCode())){
        messageService.logInfo(expression.messageCode(), expression.args());
      }
    }
  }
}
