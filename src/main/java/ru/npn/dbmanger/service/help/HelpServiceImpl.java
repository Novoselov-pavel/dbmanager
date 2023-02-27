package ru.npn.dbmanger.service.help;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;
import ru.npn.dbmanger.model.commandline.CommandLineOperation;
import ru.npn.dbmanger.service.message.MessageService;

/**
 * @author Новоселов Павел
 */
@Service
@RequiredArgsConstructor
public class HelpServiceImpl implements HelpService {
  private final MessageService messageService;


  @Override
  public boolean process(@NonNull final CommandLineArgs args) {
    if(hasHelpCommand(args)){


      //TODO вывод справки
      return true;
    }
    return false;
  }


  private boolean hasHelpCommand(final CommandLineArgs args){
    return args.operations().stream().anyMatch(CommandLineOperation::isHelpOperation);
  }
}
