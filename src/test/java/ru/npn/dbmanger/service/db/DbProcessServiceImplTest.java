package ru.npn.dbmanger.service.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.npn.dbmanger.TestDataBuilder;
import ru.npn.dbmanger.model.commandline.CommandLineOperation;
import ru.npn.dbmanger.model.commandline.DatabaseType;
import ru.npn.dbmanger.service.db.operation.liquibase.LiquibaseOperationProvider;
import ru.npn.dbmanger.service.hikari.config.HikariConfigFactory;
import ru.npn.dbmanger.service.hikari.config.HikariDatasourceLiquibaseBuilder;
import ru.npn.dbmanger.service.message.MessageService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DbProcessServiceImplTest {

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
    service = new DbProcessServiceImpl(hikariConfigFactory,
        messageService,
        liquibaseOperationProviders,
        hikariDatasourceLiquibaseBuilder);
  }

  @Test
  void processWithoutCommonOperation() {
    LiquibaseOperationProvider provider = Mockito.mock(LiquibaseOperationProvider.class);
    Mockito.when(provider.getOperation()).thenReturn(CommandLineOperation.UPDATE);
    Mockito.when(provider.isDatabaseTypeSupported(DatabaseType.POSTGRES)).thenReturn(true);
    liquibaseOperationProviders.add(provider);

    service.process(TestDataBuilder.argsWithoutCommonOperation(), null);

    Mockito.verify(messageService,Mockito.times(1)).logInfo(Mockito.eq("db.liquibase.operation.start"), Mockito.any());
    Mockito.verify(messageService,Mockito.times(1)).logInfo(Mockito.eq("db.liquibase.operation.end"), Mockito.any());

  }
}
