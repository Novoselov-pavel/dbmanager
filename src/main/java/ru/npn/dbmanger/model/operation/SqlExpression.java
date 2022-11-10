package ru.npn.dbmanger.model.operation;

import lombok.NonNull;
import org.springframework.lang.Nullable;

/**
 * Выполняемое выражение sql
 * @param sqlExpression выражение SQL
 * @param messageCode сообщение для пользователя
 * @author Новоселов Павел
 */
public record SqlExpression(@NonNull String sqlExpression, @Nullable String messageCode){}
