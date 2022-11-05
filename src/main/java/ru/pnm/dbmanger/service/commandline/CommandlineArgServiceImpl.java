package ru.pnm.dbmanger.service.commandline;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;
import ru.pnm.dbmanger.model.commandline.CommandLineArgs;
import ru.pnm.dbmanger.model.commandline.CommandLineOperation;

import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Получает настройки из аргументов командной строки
 *
 * @author Новоселов Павел
 */
@Service
@RequiredArgsConstructor
public class CommandlineArgServiceImpl implements CommandlineArgService {

  private final ApplicationArguments applicationArguments;

  @Override
  public CommandLineArgs getCommandLineArg() {
    Set<CommandLineOperation> operations = getOperationFromArgs(applicationArguments.getSourceArgs());
    String dbUrl = null;
    String adminUserName = null;
    String adminPassword = null;
    String dbUserName = null;
    String dbUserPassword = null;
    String dbName = null;
    String schema = null;
    Map<String, String> additionalProperties = new HashMap<>();

//    System.out.println("getOptionNames()");
//    Arrays.stream(applicationArguments.getOptionNames().stream().toArray()).forEach(x-> System.out.println(x));

    return new CommandLineArgs(operations,
        dbUrl,
        adminUserName,
        adminPassword,
        dbUserName,
        dbUserPassword,
        dbName,
        schema,
        additionalProperties);
  }

  private Set<CommandLineOperation> getOperationFromArgs(String[] args){
    if(isNull(args) || args.length == 0){
      return Collections.emptySet();
    }
    Set<CommandLineOperation> commandLineOperations = new HashSet<>();
    for (String arg : args) {
      CommandLineOperation operation = CommandLineOperation.getOperation(arg);
      if(nonNull(operation)){
        commandLineOperations.add(operation);
      }
    }
    return commandLineOperations;
  }



}
