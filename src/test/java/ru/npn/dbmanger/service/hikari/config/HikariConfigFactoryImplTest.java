package ru.npn.dbmanger.service.hikari.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.npn.dbmanger.TestDataBuilder;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;
import ru.npn.dbmanger.service.url.UrlBuilder;
import ru.npn.dbmanger.service.url.UrlBuilderImpl;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;
class HikariConfigFactoryImplTest {

  private UrlBuilder urlBuilder = new UrlBuilderImpl();

  private HikariConfigFactory service;

  @BeforeEach
  void setUp() {
    service = new HikariConfigFactoryImpl(urlBuilder);
  }

  @Test
  void getConfigForLiquibase() {
    CommandLineArgs args = TestDataBuilder.argsWithCreateDbSchemaCommonOperation();
    assertThat(service.getConfigForLiquibase(args))
        .matches(config->config.getJdbcUrl().equals(args.dbUrl() + args.dbName()),
            "dburl is args.dbUrl() + args.dbName()")
        .matches(config->config.getUsername().equals(args.dbUserName()),
            "userName is dbUserName")
        .matches(config->config.getPassword().equals(args.dbUserPassword()),
            "password is dbUserPassword");
  }

  @Test
  void getConfigForDatabaseForCommonOperation() {
    CommandLineArgs args = TestDataBuilder.argsWithCreateDbSchemaCommonOperation();
    assertThat(service.getConfigForDatabaseForCommonOperation(args))
        .matches(config->config.getJdbcUrl().equals(args.dbUrl() + args.dbName()),
            "dburl is args.dbUrl() + args.dbName()")
        .matches(config->config.getUsername().equals(args.adminUserName()),
            "userName is admin")
        .matches(config->config.getPassword().equals(args.adminPassword()),
            "password is adminPassword");
  }
}
