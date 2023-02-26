package ru.npn.dbmanger.service.db.sql;

import ru.npn.dbmanger.model.operation.SqlExpression;
import ru.npn.dbmanger.service.message.MessageService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author Новоселов Павел
 */
public class SqlStatementRunner {
  private SqlStatementRunner() {
  }

  public static void runExpression(MessageService messageService, Connection connection, SqlExpression expression) throws SQLException {
    try (PreparedStatement statement = connection.prepareStatement(expression.sqlExpression())) {
      statement.execute();
      if(Objects.nonNull(expression.messageCode())){
        messageService.logInfo(expression.messageCode(), expression.args());
      }
    }
  }
}
