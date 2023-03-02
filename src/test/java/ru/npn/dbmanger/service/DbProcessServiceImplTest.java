package ru.npn.dbmanger.service;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.npn.dbmanger.TestDataBuilder;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;
import ru.npn.dbmanger.service.hikari.config.HikariConfigFactory;
import ru.npn.dbmanger.service.hikari.config.HikariDatasourceLiquibaseBuilder;
import ru.npn.dbmanger.service.message.MessageService;
import ru.npn.dbmanger.service.operation.CommonDatabaseOperationService;
import ru.npn.dbmanger.service.operation.liquibase.LiquibaseOperationProvider;
import ru.npn.dbmanger.service.operation.liquibase.LiquibaseOperationProviderImpl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(MockitoExtension.class)
class DbProcessServiceImplTest {

  @Mock
  private CommonDatabaseOperationService commonDatabaseOperationService;
  private CommandLineArgs args = TestDataBuilder.argsWithoutCommonOperation();
  @Mock
  private HikariConfigFactory hikariConfigFactory;
  @Mock
  private MessageService messageService;
  private List<LiquibaseOperationProvider> liquibaseOperationProviders;
  @Mock
  private HikariDatasourceLiquibaseBuilder hikariDatasourceLiquibaseBuilder;

  private DbProcessService service;

  @BeforeEach
  void setUp() {
    liquibaseOperationProviders = new ArrayList<>();
    liquibaseOperationProviders.add(new LiquibaseOperationProviderImpl());
    service = new DbProcessServiceImpl(commonDatabaseOperationService,
        args,
        hikariConfigFactory,
        messageService,
        liquibaseOperationProviders,
        hikariDatasourceLiquibaseBuilder);
  }

  @Test
  void processWithFailCommon() {
    Mockito.when(commonDatabaseOperationService.processCommonOperations()).thenReturn(false);
    assertThat(service.process()).isFalse();
  }
}
