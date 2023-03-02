package ru.npn.dbmanger.model.commandline;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CommandLineOptionTest {

  @Test
  void getOptionUnknown() {
    assertThat(CommandLineOption.getOption(null)).isNull();
    assertThat(CommandLineOption.getOption("absent-option")).isNull();

  }

  @Test
  void getOptionFromValues() {
    for (CommandLineOption value : CommandLineOption.values()) {
      assertThat(CommandLineOption.getOption(value.getOptionName())).isEqualTo(value);
    }
  }

  @Test
  void getOptionFromUpperCaseValues() {
    for (CommandLineOption value : CommandLineOption.values()) {
      assertThat(CommandLineOption.getOption(value.getOptionName().toUpperCase())).isEqualTo(value);
    }
  }
}
