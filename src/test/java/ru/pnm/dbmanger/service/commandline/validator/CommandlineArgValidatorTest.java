package ru.pnm.dbmanger.service.commandline.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.pnm.dbmanger.exception.CommandLineArgValidationException;
import ru.pnm.dbmanger.model.commandline.CommandLineArgs;
import ru.pnm.dbmanger.model.commandline.CommandLineOperation;

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
  void validateFailValues() {

    assertThatThrownBy(()->service.validate(new CommandLineArgs(Collections.emptySet(),
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        Collections.emptyMap()))).isInstanceOf(CommandLineArgValidationException.class);

    Set<CommandLineOperation> operations = new HashSet<>();
    operations.add(CommandLineOperation.CREATE);
    assertThatThrownBy(()->service.validate(new CommandLineArgs(operations,
        "jdbc:postgresql://",
        null,
        "null",
        "null",
        "null",
        "null",
        null,
        Collections.emptyMap()))).isInstanceOf(CommandLineArgValidationException.class);


    assertThatThrownBy(()->service.validate(new CommandLineArgs(operations,
        "jdbc:postgresql://",
        null,
        "null",
        "null",
        "null",
        "null",
        null,
        Collections.emptyMap()))).isInstanceOf(CommandLineArgValidationException.class);

    assertThatThrownBy(()->service.validate(new CommandLineArgs(operations,
        "jdbc:postgresql://",
        "null",
        null,
        "null",
        "null",
        "null",
        null,
        Collections.emptyMap()))).isInstanceOf(CommandLineArgValidationException.class);

    assertThatThrownBy(()->service.validate(new CommandLineArgs(operations,
        "jdbc:postgresql://",
        "null",
        "null",
        null,
        "null",
        "null",
        null,
        Collections.emptyMap()))).isInstanceOf(CommandLineArgValidationException.class);

    assertThatThrownBy(()->service.validate(new CommandLineArgs(operations,
        "jdbc:postgresql://",
        "null",
        "null",
        "null",
        null,
        "null",
        null,
        Collections.emptyMap()))).isInstanceOf(CommandLineArgValidationException.class);

    assertThatThrownBy(()->service.validate(new CommandLineArgs(operations,
        "jdbc:postgresql://",
        "null",
        "null",
        "null",
        "null",
        null,
        null,
        Collections.emptyMap()))).isInstanceOf(CommandLineArgValidationException.class);
  }

  @Test
  void validate() {
    try {
      Set<CommandLineOperation> operations = new HashSet<>();
      operations.add(CommandLineOperation.CREATE);
      service.validate(new CommandLineArgs(operations,
          "jdbc:postgresql://",
          "null",
          "null",
          "null",
          "null",
          "null",
          null,
          Collections.emptyMap()));
    } catch (CommandLineArgValidationException e) {
      e.printStackTrace();
      fail();
    }
  }
}
