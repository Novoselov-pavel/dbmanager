package ru.pnm.dbmanger.service.operation;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.pnm.dbmanger.model.commandline.CommandLineArgs;
import ru.pnm.dbmanger.model.commandline.CommandLineOperation;

import javax.annotation.PostConstruct;

/**
 * @author Новоселов Павел
 */
public class CommonDatabaseOperationServiceImpl implements CommonDatabaseOperationService {
  private final HikariDataSource hikariDataSource;

  private final CommandLineArgs args;

  public CommonDatabaseOperationServiceImpl(@Qualifier("dataSourceForCommonOperation")HikariDataSource hikariDataSource,
                                            CommandLineArgs args) {
    this.hikariDataSource = hikariDataSource;
    this.args = args;
  }

  @PostConstruct
  public void init(){
    processCommonOperation();
  }

  @Override
  public void processCommonOperation() {
    if(!CommandLineOperation.hasCommonDatabaseOperation(args.operations())){
      return;
    }

    //TODO остановился тут

  }
}
