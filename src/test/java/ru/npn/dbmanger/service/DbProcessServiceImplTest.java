package ru.npn.dbmanger.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.npn.dbmanger.TestDataBuilder;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;
import ru.npn.dbmanger.service.db.DbProcessService;
import ru.npn.dbmanger.service.db.DbProcessServiceImpl;
import ru.npn.dbmanger.service.hikari.config.HikariConfigFactory;
import ru.npn.dbmanger.service.hikari.config.HikariDatasourceLiquibaseBuilder;
import ru.npn.dbmanger.service.message.MessageService;
import ru.npn.dbmanger.service.db.operation.CommonDatabaseOperationService;
import ru.npn.dbmanger.service.db.operation.liquibase.LiquibaseOperationProvider;
import ru.npn.dbmanger.service.db.operation.liquibase.LiquibaseOperationProviderImpl;

import java.util.ArrayList;
import java.util.List;

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
    service = new DbProcessServiceImpl(hikariConfigFactory,
        messageService,
        liquibaseOperationProviders,
        hikariDatasourceLiquibaseBuilder);
  }

  @Test
  void processWithFailCommon() {
    Mockito.when(commonDatabaseOperationService.processCommonOperations()).thenReturn(false);
    assertThat(service.process(args, commonDatabaseOperationService)).isFalse();
  }
}
