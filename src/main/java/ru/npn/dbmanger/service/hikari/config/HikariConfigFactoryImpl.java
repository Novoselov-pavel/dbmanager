package ru.npn.dbmanger.service.hikari.config;

import com.zaxxer.hikari.HikariConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;

import java.util.Objects;

/**
 * @author Новоселов Павел
 */
@Service
@RequiredArgsConstructor
public class HikariConfigFactoryImpl implements HikariConfigFactory {
  private static final int MAX_POOL_SIZE = 15;
  private static final String INIT_SQL = "SELECT 1";
  private static final String TEST_QUERY = "SELECT 1";

  @Override
  public HikariConfig getConfigForLiquibase(CommandLineArgs args) {
    HikariConfig config = new HikariConfig();
    config.setAutoCommit(true);
    config.setJdbcUrl(args.dbUrl());
    config.addDataSourceProperty("dataSource.databaseName", args.dbName());
    if (Objects.nonNull(args.schema())) {
      config.addDataSourceProperty("dataSource.schema", args.schema());
    }
    config.setUsername(args.adminUserName());
    config.setPassword(args.adminPassword());
    config.setMaximumPoolSize(MAX_POOL_SIZE);
    config.setConnectionInitSql(INIT_SQL);
    config.setConnectionTestQuery(TEST_QUERY);
    return config;
  }


  @Override
  public HikariConfig getConfigForDatabaseForCommonOperation(CommandLineArgs args) {
    HikariConfig config = new HikariConfig();
    config.setAutoCommit(true);
    config.setJdbcUrl(args.dbUrl());
    config.setUsername(args.adminUserName());
    config.setPassword(args.adminPassword());
    config.setMaximumPoolSize(MAX_POOL_SIZE);
    config.setConnectionInitSql(INIT_SQL);
    config.setConnectionTestQuery(TEST_QUERY);
    config.setAutoCommit(false);
    return config;
  }

}
