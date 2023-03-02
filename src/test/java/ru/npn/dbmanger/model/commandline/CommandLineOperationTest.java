package ru.npn.dbmanger.model.commandline;

import org.junit.jupiter.api.Test;

import java.util.List;

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

  @Test
  void getListByOrder() {
    List<CommandLineOperation> operations = CommandLineOperation.getListByOrder();
    int prevVal = Integer.MIN_VALUE;
    for (int i = 0; i < operations.size(); i++) {
      if (i == 0) {
        prevVal = operations.get(i).getOrder();
      } else {
        int curVal = operations.get(i).getOrder();
        assertThat(curVal>prevVal).isTrue();
        prevVal = curVal;
      }
    }
  }
}
