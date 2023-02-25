package ru.npn.dbmanger.service.commandline.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.npn.dbmanger.exception.CommandLineArgValidationException;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;
import ru.npn.dbmanger.model.commandline.CommandLineOperation;
import ru.npn.dbmanger.model.commandline.DatabaseType;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.*;

/**
 * Валидатор для аргументов командной строки
 *
 * @author Новоселов Павел
 */
@RequiredArgsConstructor
@Service
public class CommandlineArgValidatorImpl implements CommandlineArgValidator {
  private static final String CMD_ARG_IS_NULL = "cmd.arg.is.null";
  private static final String URL_IS_NULL = "url.is.null";
  private static final String DB_IS_NOT_SUPPORTED = "db.is.not.supported";
  private static final String ADMIN_IS_NULL = "admin.is.null";
  private static final String ADMIN_PASSWORD_IS_NULL = "admin.password.is.null";
  private static final String DB_USER_NAME_IS_NULL = "db.user.is.null";
  private static final String DB_USER_PASSWORD_IS_NULL = "db.user.password.is.null";
  private static final String DB_NAME_IS_NULL = "db.name.is.null";


  @Override
  public void validate(CommandLineArgs args) throws CommandLineArgValidationException {

    final List<CommandLineArgValidationException.ExceptionReason> exceptionReasons = new ArrayList<>();
    boolean check = true;

    if(isNull(args)){
      exceptionReasons.add(new CommandLineArgValidationException.ExceptionReason(CMD_ARG_IS_NULL,null));
      throw new CommandLineArgValidationException(exceptionReasons);
    }

    if(hasHelpCommand(args)){
      return;
    }

    if(isNull(args.dbUrl())){
      exceptionReasons.add(new CommandLineArgValidationException.ExceptionReason(URL_IS_NULL,null));
      check = false;
    }

    DatabaseType type = DatabaseType.getTypeFromDbUrl(args.dbUrl());
    if(isNull(type)){
      exceptionReasons.add(new CommandLineArgValidationException.ExceptionReason(DB_IS_NOT_SUPPORTED,null));
      check = false;
    }

    if(isNull(args.adminUserName())){
      exceptionReasons.add(new CommandLineArgValidationException.ExceptionReason(ADMIN_IS_NULL,null));
      check = false;
    }

    if(isNull(args.adminPassword())){
      exceptionReasons.add(new CommandLineArgValidationException.ExceptionReason(ADMIN_PASSWORD_IS_NULL,null));
      check = false;
    }

    if(isNull(args.dbUserName())){
      exceptionReasons.add(new CommandLineArgValidationException.ExceptionReason(DB_USER_NAME_IS_NULL,null));
      check = false;
    }

    if(isNull(args.dbUserPassword())){
      exceptionReasons.add(new CommandLineArgValidationException.ExceptionReason(DB_USER_PASSWORD_IS_NULL,null));
      check = false;
    }

    if(isNull(args.dbName())){
      exceptionReasons.add(new CommandLineArgValidationException.ExceptionReason(DB_NAME_IS_NULL,null));
      check = false;
    }

    if(!check){
      throw new CommandLineArgValidationException(exceptionReasons);
    }


  }

  private boolean hasHelpCommand(CommandLineArgs args){
    for (CommandLineOperation operation : args.operations()) {
      if(CommandLineOperation.HELP == operation){
        return true;
      }
    }

    return false;
  }
}
