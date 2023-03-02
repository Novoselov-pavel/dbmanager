package ru.npn.dbmanger.service.db.operation.postgres;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import liquibase.pro.packaged.E;
import liquibase.pro.packaged.S;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.utility.DockerImageName;
import ru.npn.dbmanger.TestDataBuilder;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;
import ru.npn.dbmanger.model.operation.SqlExpression;
import ru.npn.dbmanger.service.db.operation.CommonOperationProvider;
import ru.npn.dbmanger.service.db.sql.SqlStatementRunner;
import ru.npn.dbmanger.service.message.MessageService;

import javax.sql.DataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PostgresOperationProviderTest {
  private static final Logger logger = LogManager.getLogger(PostgresOperationProviderTest.class);

  private CommonOperationProvider createSchemaProvider = new PostgresCreateSchemeOperationProvider();

  private CommonOperationProvider dropSchemaProvider = new PostgresDropSchemeOperationProvider();

  @Mock
  private MessageService messageService;

  private EmbeddedPostgres embeddedPostgres;
  private DataSource dataSource;
  private CommandLineArgs args;


  @BeforeEach
  void setUp() throws IOException, SQLException {
    EmbeddedPostgres.Builder builder = EmbeddedPostgres.builder();
    args  = TestDataBuilder.argsWithCreateDbSchemaCommonOperation();
    embeddedPostgres = builder.setDatabaseName("postgres").start();
    dataSource = embeddedPostgres.getPostgresDatabase();
    createDb(args.dbName(), args.adminUserName());
  }

  @Test
  void createSchema() throws IOException {
    try {
      List<SqlExpression> expressions =  createSchemaProvider.getExpressions(args);
      runExpressionList(expressions);
    } catch (Exception e){
      logger.error(e.getMessage(), e);
      fail();
    }
    embeddedPostgres.close();
  }

  private void runExpressionList(List<SqlExpression> expressions) throws SQLException {
    try(Connection connection = dataSource.getConnection()) {
      for (SqlExpression expression : expressions) {
        logger.info(expression.sqlExpression());
        SqlStatementRunner.runExpression(messageService,connection, expression);
      }
    }
  }



  @Test
  void createAndDropSchema() throws IOException {
    try {
      List<SqlExpression> createExpressions =  createSchemaProvider.getExpressions(args);
      List<SqlExpression> dropExpressions =  dropSchemaProvider.getExpressions(args);
      runExpressionList(createExpressions);
      runExpressionList(dropExpressions);
    } catch (Exception e){
      logger.error(e.getMessage(), e);
      fail();
    }
    embeddedPostgres.close();

  }


  private void createDb(String dbName, String adminName) throws SQLException {
    String sqlCreate = "CREATE DATABASE "+dbName+
        "    WITH " +
        "    OWNER = "+adminName+" " +
        "    ENCODING = 'UTF8' " +
        "    LC_COLLATE = 'en_US.utf8' " +
        "    LC_CTYPE = 'en_US.utf8' " +
        "    TABLESPACE = pg_default " +
        "    CONNECTION LIMIT = -1 " +
        "    IS_TEMPLATE = False;";

    String sqlGrant = "GRANT ALL ON DATABASE "+dbName+" TO "+adminName+";";
    execute(sqlCreate);
    execute(sqlGrant);

  }

  private void execute(String sql) throws SQLException {
    try(Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.execute();
      }
    }
  }

}
