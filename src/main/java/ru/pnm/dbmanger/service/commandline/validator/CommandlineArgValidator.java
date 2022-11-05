package ru.pnm.dbmanger.service.commandline.validator;

import ru.pnm.dbmanger.exception.CommandLineArgValidationException;
import ru.pnm.dbmanger.model.commandline.CommandLineArgs;

/**
 * Валидатор для аргументов командной строки
 *
 * @author Новоселов Павел
 */
public interface CommandlineArgValidator {

  /**
   * Проверяет аргументы команднйо строки на валидность
   *
   * @param args аргументы команжной строки
   * @throws CommandLineArgValidationException
   */
  void validate(CommandLineArgs args) throws CommandLineArgValidationException;

}
