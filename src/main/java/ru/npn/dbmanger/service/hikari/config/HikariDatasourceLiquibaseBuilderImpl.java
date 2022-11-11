package ru.npn.dbmanger.service.hikari.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Service;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;

/**
 * @author Новоселов Павел
 */
@Service
public class HikariDatasourceLiquibaseBuilderImpl implements HikariDatasourceLiquibaseBuilder {
  @Override
  public HikariDataSource build(CommandLineArgs args, HikariConfigFactory hikariConfigFactory) {
    return new HikariDataSource(hikariConfigFactory.getConfigForLiquibase(args));
  }
}
