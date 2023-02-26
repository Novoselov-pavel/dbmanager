package ru.npn.dbmanger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.npn.dbmanger.exception.CommandLineArgValidationException;
import ru.npn.dbmanger.exception.DbUpdateException;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;
import ru.npn.dbmanger.service.commandline.CommandlineArgService;
import ru.npn.dbmanger.service.commandline.validator.CommandlineArgValidator;
import ru.npn.dbmanger.service.db.DbProcessService;
import ru.npn.dbmanger.service.db.operation.CommonDatabaseOperationService;
import ru.npn.dbmanger.service.db.operation.CommonOperationFactory;
import ru.npn.dbmanger.service.db.operation.CommonOperationProvider;
import ru.npn.dbmanger.service.help.HelpService;
import ru.npn.dbmanger.service.hikari.config.HikariConfigFactory;
import ru.npn.dbmanger.service.message.MessageService;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Новоселов Павел
 */
@Service
@RequiredArgsConstructor
public class DbManagerServiceImpl implements DbManagerService {
  private static final String LOG_SETTING = "start.with.setting";

  private final DbProcessService dbProcessService;
  private final HelpService helpService;
  private final CommandlineArgService commandlineArgService;
  private final CommandlineArgValidator validator;
  private final HikariConfigFactory hikariConfigFactory;
  private final MessageService messageService;
  private final List<CommonOperationProvider> operationsProviders;
  private final CommonOperationFactory commonOperationFactory;


  @PostConstruct
  @Override
  public void execute() {
    CommandLineArgs args = commandlineArgService.getCommandLineArg();
    if(helpService.process(args)){
      return;
    }
    try {
      validator.validate(args);
      messageService.logInfo(LOG_SETTING, new String[]{args.argumentToPrettyString()});
    } catch (CommandLineArgValidationException e) {
      messageService.log(e);
      return;
    }
    CommonDatabaseOperationService commonDatabaseOperationService = commonOperationFactory.createCommonOperationService(args,
        hikariConfigFactory, operationsProviders, messageService);
    boolean result = dbProcessService.process(args, commonDatabaseOperationService);
    if(!result){
      throw new DbUpdateException();
    }
  }


}
