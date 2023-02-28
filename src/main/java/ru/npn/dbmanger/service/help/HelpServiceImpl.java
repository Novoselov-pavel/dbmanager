package ru.npn.dbmanger.service.help;

import lombok.*;
import org.springframework.stereotype.Service;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;
import ru.npn.dbmanger.model.commandline.CommandLineOperation;
import ru.npn.dbmanger.model.commandline.CommandLineOption;
import ru.npn.dbmanger.service.message.MessageService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Новоселов Павел
 */
@Service
@RequiredArgsConstructor
public class HelpServiceImpl implements HelpService {
  private static final String OPERATION_TITLE = "operation.help.title.operation";
  private static final String OPERATION_LINE = "operation.help.line.format";
  private static final String OPTION_TITLE = "option.help.title";
  private static final String OPTION_LINE = "option.help.line.format";

  private final MessageService messageService;


  @Override
  public boolean process(@NonNull final CommandLineArgs args) {
    if(hasHelpCommand(args)){
      messageService.logInfo(OPERATION_TITLE);

      HelpConsoleMessages operationMessages = getData(Arrays.asList(CommandLineOperation.values()),
          x->((CommandLineOperation)x).getFullOperationName() + ", " + ((CommandLineOperation)x).getShortOperationName() ,
          x->getDescription((CommandLineOperation)x));

      HelpConsoleMessages optionMessages = getData(Arrays.asList(CommandLineOption.values()),
          x->((CommandLineOption)x).getOption(),
          x->getDescription((CommandLineOption)x));

      for (HelpConsoleMessage message : operationMessages.getMessages()) {
        messageService.logInfo(OPERATION_LINE, message.getColumn1(), message.getColumn2());
      }
      messageService.logInfo(OPTION_TITLE);
      for (HelpConsoleMessage message : optionMessages.getMessages()) {
        messageService.logInfo(OPTION_LINE, message.getColumn1(), message.getColumn2());
      }
      return true;
    }
    return false;
  }

  private String getDescription(CommandLineOperation operation){
    return messageService.getMessageString(operation.getDescriptionKey());
  }

  private String getDescription(CommandLineOption option){
    return messageService.getMessageString(option.getDescriptionKey());
  }

  private HelpConsoleMessages getData(Collection<Object> objects, ExtractMessageData extractColumn1, ExtractMessageData extractColumn2){
    HelpConsoleMessages messages = new HelpConsoleMessages();
    for (Object object : objects) {
      HelpConsoleMessage message = new HelpConsoleMessage(extractColumn1.extract(object), extractColumn2.extract(object));
      messages.addMessage(message);
    }
    return messages;
  }


  private boolean hasHelpCommand(final CommandLineArgs args){
    return args.operations().stream().anyMatch(CommandLineOperation::isHelpOperation);
  }

  static class HelpConsoleMessages{
    private int column1MaxSize = 0;
    private List<HelpConsoleMessage> messages = new ArrayList<>();

    public void addMessage(final HelpConsoleMessage consoleMessage){
      int size = consoleMessage.getColumn1().length();
      if(size>column1MaxSize){
        column1MaxSize = size;
        updateColumn1LengthToSize(size);
      } else if(size<column1MaxSize){
        updateColumn1LengthToSizeForMessage(consoleMessage, column1MaxSize);
      }
      messages.add(consoleMessage);
    }

    private void updateColumn1LengthToSize(int size){
      for (HelpConsoleMessage message : messages) {
        updateColumn1LengthToSizeForMessage(message, size);
      }
    }

    private void updateColumn1LengthToSizeForMessage(final HelpConsoleMessage consoleMessage, int size){
      StringBuilder builder = new StringBuilder(consoleMessage.getColumn1());
      for (int i = consoleMessage.getColumn1().length(); i <size; i++) {
        builder.append(" ");
      }
      consoleMessage.setColumn1(builder.toString());
    }

    public List<HelpConsoleMessage> getMessages() {
      return messages;
    }
  }


  @AllArgsConstructor
  @Getter
  @Setter
  static class HelpConsoleMessage{
    private @NonNull String column1;
    private String column2;
  }
}
