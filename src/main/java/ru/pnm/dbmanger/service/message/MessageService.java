package ru.pnm.dbmanger.service.message;

import ru.pnm.dbmanger.exception.CommandLineArgValidationException;

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
}
