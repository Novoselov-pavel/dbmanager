package ru.pnm.dbmanger.service.message;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import ru.pnm.dbmanger.exception.CommandLineArgValidationException;

import java.util.Locale;

/**
 * @author Новоселов Павел
 */
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
  private static final Logger logger = LogManager.getLogger();
  private final MessageSource messageSource;

  @Override
  public void log(CommandLineArgValidationException exception) {
    Locale locale = LocaleContextHolder.getLocale();
    for (CommandLineArgValidationException.ExceptionReason reason : exception.getExceptionReasons()) {
      logger.error(messageSource.getMessage(reason.reason(), reason.args(), locale));
    }
  }
}
