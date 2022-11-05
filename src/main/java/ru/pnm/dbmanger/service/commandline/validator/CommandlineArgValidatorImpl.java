package ru.pnm.dbmanger.service.commandline.validator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import ru.pnm.dbmanger.exception.CommandLineArgValidationException;
import ru.pnm.dbmanger.model.commandline.CommandLineArgs;

import java.util.Locale;

/**
 * Валидатор для аргументов командной строки
 *
 * @author Новоселов Павел
 */
@RequiredArgsConstructor
@Service
public class CommandlineArgValidatorImpl implements CommandlineArgValidator {


  @Override
  public void validate(@NonNull CommandLineArgs args) throws CommandLineArgValidationException {
    System.out.println(LocaleContextHolder.getLocale());

  }



}
