package ru.npn.dbmanger.service.help;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;
import ru.npn.dbmanger.model.commandline.CommandLineOperation;

/**
 * @author Новоселов Павел
 */
@Service
@RequiredArgsConstructor
public class HelpServiceImpl implements HelpService {
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
