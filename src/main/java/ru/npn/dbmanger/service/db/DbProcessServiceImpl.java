package ru.npn.dbmanger.service.db;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;
import ru.npn.dbmanger.model.commandline.CommandLineOperation;
import ru.npn.dbmanger.model.commandline.DatabaseType;
import ru.npn.dbmanger.service.db.operation.CommonDatabaseOperationService;
import ru.npn.dbmanger.service.db.operation.liquibase.LiquibaseOperationProvider;
import ru.npn.dbmanger.service.hikari.config.HikariConfigFactory;
import ru.npn.dbmanger.service.hikari.config.HikariDatasourceLiquibaseBuilder;
import ru.npn.dbmanger.service.message.MessageService;

import java.util.List;

import static java.util.Objects.isNull;

/**
 * @author Новоселов Павел
 */
@Service
@RequiredArgsConstructor
public class DbProcessServiceImpl implements DbProcessService {
  private static final String ERROR_PROVIDER_NOT_FOUND_CODE = "db.liquibase.operation.provider.not.found";
  private static final String START_OPERATION_CODE = "db.liquibase.operation.start";
  private static final String END_OPERATION_CODE = "db.liquibase.operation.end";

  private final HikariConfigFactory hikariConfigFactory;
  private final MessageService messageService;
  private final List<LiquibaseOperationProvider> liquibaseOperationProviders;
  private final HikariDatasourceLiquibaseBuilder hikariDatasourceLiquibaseBuilder;


  @Override
  public boolean process(CommandLineArgs args, @Nullable CommonDatabaseOperationService commonDatabaseOperationService) {
    boolean isCommonOperationSuccess = runCommonOperation(commonDatabaseOperationService);
    if (!isCommonOperationSuccess) {
      return false;
    }
    final List<CommandLineOperation> commandLineOperations = args.operations().stream().filter(x -> !x.isCommonOperation()).toList();
    if (commandLineOperations.isEmpty()) {
      return true;
    }
    HikariDataSource dataSource = hikariDatasourceLiquibaseBuilder.build(args, hikariConfigFactory);

    for (CommandLineOperation operation : commandLineOperations) {
      messageService.logInfo(START_OPERATION_CODE, operation.toString());
      LiquibaseOperationProvider operationProvider = getProviderForOperation(operation, args.databaseType());
      if (isNull(operationProvider)) {
        String databaseType = args.databaseType() == null ? "null" : args.databaseType().toString();
        messageService.logError(ERROR_PROVIDER_NOT_FOUND_CODE, databaseType, operation.toString());
        return false;
      }
      operationProvider.process(dataSource, args);
      messageService.logInfo(END_OPERATION_CODE, operation.toString());
    }
    return true;
  }

  private boolean runCommonOperation(@Nullable CommonDatabaseOperationService commonDatabaseOperationService){
    if(isNull(commonDatabaseOperationService)){
      return true;
    }
    return commonDatabaseOperationService.processCommonOperations();
  }

  private LiquibaseOperationProvider getProviderForOperation(CommandLineOperation operation, DatabaseType databaseType) {
    return liquibaseOperationProviders.stream()
        .filter(x -> operation == x.getOperation() && x.isDatabaseTypeSupported(databaseType))
        .findFirst()
        .orElse(null);
  }
}
