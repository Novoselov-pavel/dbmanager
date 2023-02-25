package ru.npn.dbmanger.service.db.operation;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.npn.dbmanger.TestDataBuilder;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;
import ru.npn.dbmanger.service.message.MessageService;
import ru.npn.dbmanger.service.db.operation.postgres.PostgresCreateSchemeOperationProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    operations.add(new PostgresCreateSchemeOperationProvider());
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
  void processWithCommonOperations() throws SQLException {
    CommandLineArgs args = TestDataBuilder.argsWithCreateDbSchemaCommonOperation();
    Connection connection = Mockito.mock(Connection.class);
    PreparedStatement statement = Mockito.mock(PreparedStatement.class);
    Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
    Mockito.when(hikariDataSource.getConnection()).thenReturn(connection);
    service = new CommonDatabaseOperationServiceImpl(hikariDataSource,
        operations,
        messageService,
        args);
    service.processCommonOperations();
    Mockito.verify(hikariDataSource, Mockito.times(1)).getConnection();
    Mockito.verify(connection, Mockito.times(3)).prepareStatement(Mockito.anyString());
  }
}
