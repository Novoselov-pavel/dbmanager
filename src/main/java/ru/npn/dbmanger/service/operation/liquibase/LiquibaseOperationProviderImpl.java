package ru.npn.dbmanger.service.operation.liquibase;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;
import ru.npn.dbmanger.model.commandline.CommandLineOperation;
import ru.npn.dbmanger.model.commandline.DatabaseType;

import javax.sql.DataSource;
import java.io.File;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author Новоселов Павел
 */
@Service
public class LiquibaseOperationProviderImpl implements LiquibaseOperationProvider {
  private static final Logger logger = LogManager.getLogger(LiquibaseOperationProviderImpl.class);
  @Override
  public @NonNull CommandLineOperation getOperation() {
    return CommandLineOperation.UPDATE;
  }

  @Override
  public boolean isDatabaseTypeSupported(DatabaseType databaseType) {
    return true;
  }


  @Override
  public void process(DataSource dataSource, CommandLineArgs args) {
    try (Connection c = dataSource.getConnection()) {
      Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(c));
      if(Objects.nonNull(args.schema())){
        database.setDefaultSchemaName(args.schema());
        database.setLiquibaseSchemaName(args.schema());
      }
      try(Liquibase liquibase = new Liquibase(getFileName(args.changelogPath()), new FileSystemResourceAccessor(getRootDirectory(args.changelogPath())), database)) {
        liquibase.update("main");
      }
    } catch (LiquibaseException | SQLException e) {
      logger.error(e.getMessage(), e);
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  protected File getRootDirectory(String filePath){
    Path path = Path.of(filePath);
    return path.getParent().toFile();
  }

  protected String getFileName(String filePath){
    Path path = Path.of(filePath);
    return path.getFileName().toString();
  }
}
