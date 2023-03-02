package ru.npn.dbmanger.service.commandline.validator;

import ru.npn.dbmanger.model.commandline.CommandLineArgs;
import ru.npn.dbmanger.exception.CommandLineArgValidationException;

/**
 * Валидатор для аргументов командной строки
 *
 * @author Новоселов Павел
 */
public interface CommandlineArgValidator {

  /**
   * Проверяет аргументы командной строки на валидность
   *
   * @param args аргументы командной строки
   * @throws CommandLineArgValidationException при ошибке в основных настройках
   */
  void validate(CommandLineArgs args) throws CommandLineArgValidationException;

}
