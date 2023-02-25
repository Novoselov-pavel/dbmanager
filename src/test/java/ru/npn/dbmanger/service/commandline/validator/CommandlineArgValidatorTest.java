package ru.npn.dbmanger.service.commandline.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.npn.dbmanger.exception.CommandLineArgValidationException;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;
import ru.npn.dbmanger.model.commandline.CommandLineOperation;
import ru.npn.dbmanger.model.commandline.DatabaseType;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.fail;

class CommandlineArgValidatorTest {

  private CommandlineArgValidator service;

  @BeforeEach
  void setUp() {
    service = new CommandlineArgValidatorImpl();
  }

  @Test
  void validateHeplOperation() {

    try {
      Set<CommandLineOperation> operations = new HashSet<>();
      operations.add(CommandLineOperation.HELP);
      service.validate(new CommandLineArgs(operations,null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          Collections.emptyMap()));
    } catch (CommandLineArgValidationException e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void validateFailValues() {
    assertThatThrownBy(() -> service.validate(null)).isInstanceOf(CommandLineArgValidationException.class);

    assertThatThrownBy(() -> service.validate(new CommandLineArgs(Collections.emptySet(),
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        Collections.emptyMap()))).isInstanceOf(CommandLineArgValidationException.class);

    Set<CommandLineOperation> operations = new HashSet<>();
    operations.add(CommandLineOperation.CREATE_DB);
    assertThatThrownBy(() -> service.validate(new CommandLineArgs(operations,
        DatabaseType.POSTGRES.getGetDatabaseUrlSimple(),
        null,
        "null",
        "null",
        "null",
        "null",
        null,
        null,
        null,
        Collections.emptyMap()))).isInstanceOf(CommandLineArgValidationException.class);


    assertThatThrownBy(() -> service.validate(new CommandLineArgs(operations,
        DatabaseType.POSTGRES.getGetDatabaseUrlSimple(),
        null,
        "null",
        "null",
        "null",
        "null",
        null,
        null,
        null,
        Collections.emptyMap()))).isInstanceOf(CommandLineArgValidationException.class);

    assertThatThrownBy(() -> service.validate(new CommandLineArgs(operations,
        DatabaseType.POSTGRES.getGetDatabaseUrlSimple(),
        "null",
        null,
        "null",
        "null",
        "null",
        null,
        null,
        null,
        Collections.emptyMap()))).isInstanceOf(CommandLineArgValidationException.class);

    assertThatThrownBy(() -> service.validate(new CommandLineArgs(operations,
        DatabaseType.POSTGRES.getGetDatabaseUrlSimple(),
        "null",
        "null",
        null,
        "null",
        "null",
        null,
        null,
        null,
        Collections.emptyMap()))).isInstanceOf(CommandLineArgValidationException.class);

    assertThatThrownBy(() -> service.validate(new CommandLineArgs(operations,
        DatabaseType.POSTGRES.getGetDatabaseUrlSimple(),
        "null",
        "null",
        "null",
        null,
        "null",
        null,
        null,
        null,
        Collections.emptyMap()))).isInstanceOf(CommandLineArgValidationException.class);

    assertThatThrownBy(() -> service.validate(new CommandLineArgs(operations,
        DatabaseType.POSTGRES.getGetDatabaseUrlSimple(),
        "null",
        "null",
        "null",
        "null",
        null,
        null,
        null,
        null,
        Collections.emptyMap()))).isInstanceOf(CommandLineArgValidationException.class);
  }

  @Test
  void validate() {
    try {
      Set<CommandLineOperation> operations = new HashSet<>();
      operations.add(CommandLineOperation.CREATE_DB);
      service.validate(new CommandLineArgs(operations,
          DatabaseType.POSTGRES.getGetDatabaseUrlSimple(),
          "null",
          "null",
          "null",
          "null",
          "null",
          null,
          DatabaseType.getTypeFromDbUrl(DatabaseType.POSTGRES.getGetDatabaseUrlSimple()),
          null,
          Collections.emptyMap()));
    } catch (CommandLineArgValidationException e) {
      e.printStackTrace();
      fail();
    }
  }
}
