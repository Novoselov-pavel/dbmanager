package ru.pnm.dbmanger.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.assertj.core.api.Assertions.assertThat;

class CommandLineArgValidationExceptionTest {

  @BeforeEach
  void setUp() {
  }

  @Test
  void createExceptionWithNull() {
    try {
      CommandLineArgValidationException exception = new CommandLineArgValidationException(null);
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void createExceptionWithListWithNull() {
    try {
      List<String> list = new ArrayList<>();
      list.add(null);
      CommandLineArgValidationException exception = new CommandLineArgValidationException(list);
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void getExceptionStringT() {
    List<String> list = new ArrayList<>();
    list.add("A");
    list.add(null);
    list.add("B");
    CommandLineArgValidationException exception = new CommandLineArgValidationException(list);
    assertThat(exception.getMessageString()).isEqualTo("A" + System.lineSeparator() + "B" + System.lineSeparator());
  }
}
