package ru.npn.dbmanger.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

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
      List<CommandLineArgValidationException.ExceptionReason> list = new ArrayList<>();
      CommandLineArgValidationException exception = new CommandLineArgValidationException(list);
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

}
