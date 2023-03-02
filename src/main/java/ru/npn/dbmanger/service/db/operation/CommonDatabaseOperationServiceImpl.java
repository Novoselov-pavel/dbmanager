package ru.npn.dbmanger.service.db.operation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.Assert;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;
import ru.npn.dbmanger.model.commandline.CommandLineOperation;
import ru.npn.dbmanger.model.commandline.DatabaseType;
import ru.npn.dbmanger.model.operation.SqlExpression;
import ru.npn.dbmanger.service.db.sql.SqlStatementRunner;
import ru.npn.dbmanger.service.message.MessageService;

import javax.sql.DataSource;
import java.sql.Connection;
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


  private final DataSource dataSource;
  private final List<CommonOperationProvider> operations;
  private final MessageService messageService;

  private final CommandLineArgs args;

  public CommonDatabaseOperationServiceImpl(DataSource dataSource,
                                            List<CommonOperationProvider> operations,
                                            MessageService messageService,
                                            CommandLineArgs args) {
    this.dataSource = dataSource;
    this.operations = operations;
    this.messageService = messageService;
    this.args = args;
  }

  @Override
  public boolean processCommonOperations() {
    if(Objects.isNull(dataSource)){
      return false;
    }
    if (!CommandLineOperation.hasCommonDatabaseOperation(args.operations())) {
      return true;
    }
    final List<CommandLineOperation> commonOperationByPriority = args.operations().stream()
        .filter(CommandLineOperation::isCommonOperation)
        .sorted(Comparator.naturalOrder())
        .toList();

    final DatabaseType databaseType = args.databaseType();
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
    try (Connection connection = dataSource.getConnection()) {
      connection.setAutoCommit(false);
      for (SqlExpression expression : operation.getExpressions(args)) {
        SqlStatementRunner.runExpression(messageService, connection, expression);
      }
      connection.commit();
    } catch (SQLException e) {
      logger.error(e.getMessage(), e);
      return false;
    }
    return true;
  }

}
