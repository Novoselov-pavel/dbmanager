package ru.npn.dbmanger.model.operation;

import lombok.NonNull;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.Objects;

/**
 * Выполняемое выражение sql
 *
 * @param sqlExpression выражение SQL
 * @param messageCode   сообщение для пользователя
 * @param args          аргументы сообщения
 * @author Новоселов Павел
 */
public record SqlExpression(@NonNull String sqlExpression, @Nullable String messageCode, @Nullable Object[] args) {
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SqlExpression that = (SqlExpression) o;
    return sqlExpression.equals(that.sqlExpression) && Objects.equals(messageCode, that.messageCode) && Arrays.equals(args, that.args);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(sqlExpression, messageCode);
    result = 31 * result + Arrays.hashCode(args);
    return result;
  }

  @Override
  public String toString() {
    return "SqlExpression{" +
        "sqlExpression='" + sqlExpression + '\'' +
        ", messageCode='" + messageCode + '\'' +
        ", args=" + Arrays.toString(args) +
        '}';
  }
}
