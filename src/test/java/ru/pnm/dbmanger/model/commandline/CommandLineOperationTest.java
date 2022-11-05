package ru.pnm.dbmanger.model.commandline;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CommandLineOperationTest {


  @Test
  void getOperationUnknown() {
    assertThat(CommandLineOperation.getOperation(null)).isNull();
    assertThat(CommandLineOperation.getOperation("absent-operation")).isNull();

  }

  @Test
  void getOperationFromShortValues() {
    for (CommandLineOperation value : CommandLineOperation.values()) {
      assertThat(CommandLineOperation.getOperation(value.getShortOperationName())).isEqualTo(value);
    }
  }

  @Test
  void getOperationFromFullValues() {
    for (CommandLineOperation value : CommandLineOperation.values()) {
      assertThat(CommandLineOperation.getOperation(value.getFullOperationName())).isEqualTo(value);
    }
  }

  @Test
  void getOperationFromUpperCaseShortValues() {
    for (CommandLineOperation value : CommandLineOperation.values()) {
      assertThat(CommandLineOperation.getOperation(value.getShortOperationName().toUpperCase())).isEqualTo(value);
    }
  }

  @Test
  void getOperationFromUpperCaseFullValues() {
    for (CommandLineOperation value : CommandLineOperation.values()) {
      assertThat(CommandLineOperation.getOperation(value.getFullOperationName().toUpperCase())).isEqualTo(value);
    }
  }
}
