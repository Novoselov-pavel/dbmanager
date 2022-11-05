package ru.pnm.dbmanger.service.commandline.validator;

import lombok.NonNull;
import ru.pnm.dbmanger.exception.CommandLineArgValidationException;
import ru.pnm.dbmanger.model.commandline.CommandLineArgs;

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
  void validate(@NonNull CommandLineArgs args) throws CommandLineArgValidationException;

}
