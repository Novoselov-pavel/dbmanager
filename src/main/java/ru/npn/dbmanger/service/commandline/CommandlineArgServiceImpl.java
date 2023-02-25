package ru.npn.dbmanger.service.commandline;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;
import ru.npn.dbmanger.model.commandline.CommandLineOperation;
import ru.npn.dbmanger.model.commandline.CommandLineOption;
import ru.npn.dbmanger.model.commandline.DatabaseType;

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
    final Set<CommandLineOperation> operations = getOperationFromArgs(applicationArguments.getSourceArgs());

    final Map<String, String> additionalProperties = new HashMap<>();
    final Map<CommandLineOption, String> optionMap = getOptionNameMap(applicationArguments.getOptionNames());
    final String dbUrl = getLastOptionValue(CommandLineOption.DB_URL, optionMap);
    final String adminUserName = getLastOptionValue(CommandLineOption.ADMIN_USERNAME, optionMap);
    final String adminPassword = getLastOptionValue(CommandLineOption.ADMIN_PASSWORD, optionMap);
    final String dbUserName = getLastOptionValue(CommandLineOption.DB_USERNAME, optionMap);
    final String dbUserPassword = getLastOptionValue(CommandLineOption.DB_USER_PASSWORD, optionMap);
    final String dbName = getLastOptionValue(CommandLineOption.DB_NAME, optionMap);
    final String schema = getLastOptionValue(CommandLineOption.DB_SCHEMA, optionMap);
    final String changelogPath = getLastOptionValue(CommandLineOption.CHANGELOG_PATH, optionMap);
    final DatabaseType databaseType = DatabaseType.getTypeFromDbUrl(dbUrl);
    return new CommandLineArgs(operations,
        dbUrl,
        adminUserName,
        adminPassword,
        dbUserName,
        dbUserPassword,
        dbName,
        schema,
        databaseType,
        changelogPath,
        additionalProperties);
  }

  private String getLastOptionValue(@NonNull final CommandLineOption option,
                                    final Map<CommandLineOption, String> optionMap) {
    String optionName = optionMap.get(option);
    if (isNull(optionName)) {
      return null;
    }
    final List<String> values = applicationArguments.getOptionValues(optionName);
    if (isNull(values) || values.isEmpty()) {
      return null;
    }
    return values.get(values.size() - 1);
  }

  private Set<CommandLineOperation> getOperationFromArgs(final String[] args) {
    if (isNull(args) || args.length == 0) {
      return Collections.emptySet();
    }
    final Set<CommandLineOperation> commandLineOperations = new HashSet<>();
    for (String arg : args) {
      final CommandLineOperation operation = CommandLineOperation.getOperation(arg);
      if (nonNull(operation)) {
        commandLineOperations.add(operation);
      }
    }
    return commandLineOperations;
  }

  private Map<CommandLineOption, String> getOptionNameMap(final Set<String> optionNames) {
    if (isNull(optionNames) || optionNames.isEmpty()) {
      return Collections.emptyMap();
    }

    final Map<CommandLineOption, String> retVal = new EnumMap<>(CommandLineOption.class);

    for (String optionName : optionNames) {
      final CommandLineOption option = CommandLineOption.getOption(optionName);
      if (nonNull(option)) {
        retVal.put(option, optionName);
      }
    }
    return retVal;
  }


}
