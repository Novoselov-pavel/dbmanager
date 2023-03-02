package ru.npn.dbmanger.service.message;

import org.springframework.lang.Nullable;
import ru.npn.dbmanger.exception.CommandLineArgValidationException;

/**
 * Служба отправки сообщения пользователю
 *
 * @author Новоселов Павел
 */
public interface MessageService {

  /**
   * Отправляет локализованное сообщение об исключении при проверке входных параметром
   *
   * @param exception исключение при проверке входных параметром
   */
  void log(CommandLineArgValidationException exception);

  /**
   * Логирует сообщение с уровнем Info
   * @param code код из resource bundle
   * @param args аргументы
   */
  void logInfo(String code, Object... args);

  /**
   * Логирует сообщение с уровнем Error
   * @param code код из resource bundle
   * @param args аргументы
   */
  void logError(String code, Object... args);

  /**
   * Возвращает строковое значение сообщения с учетом локали запущенного приложения
   * @param code код из resource bundle
   * @param args аргументы
   * @return локализованное сообщение
   */
  String getMessageString(String code, Object... args);
}
