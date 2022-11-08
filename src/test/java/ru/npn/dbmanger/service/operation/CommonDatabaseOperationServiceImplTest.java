package ru.npn.dbmanger.service.operation;

import com.zaxxer.hikari.HikariDataSource;
import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.npn.dbmanger.TestDataBuilder;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;
import ru.npn.dbmanger.model.commandline.CommandLineOperation;
import ru.npn.dbmanger.model.commandline.DatabaseType;
import ru.npn.dbmanger.service.message.MessageService;
import ru.npn.dbmanger.service.operation.postgres.PostgresCreateDatabaseOperationProvider;

import java.sql.SQLException;
import java.util.*;

@ExtendWith(MockitoExtension.class)
class CommonDatabaseOperationServiceImplTest {
  @Mock
  private HikariDataSource hikariDataSource;
  private List<CommonOperationProvider> operations;
  @Mock
  private MessageService messageService;
  private CommonDatabaseOperationService service;

  @BeforeEach
  void setUp() {
    operations = new ArrayList<>();
    operations.add(new PostgresCreateDatabaseOperationProvider());
  }

  @Test
  void processWithoutCommonOperations() throws SQLException {
    CommandLineArgs args = TestDataBuilder.argsWithoutCommonOperation();
    service = new CommonDatabaseOperationServiceImpl(hikariDataSource,
        operations,
        messageService,
        args);
    service.processCommonOperations();
    Mockito.verify(hikariDataSource, Mockito.times(0)).getConnection();
  }

  @Test
  void processWithCommonOperations() {
    //TODO
  }
}
