package ru.pnm.dbmanger.model.commandline;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class DatabaseTypeTest {

  @Test
  void getTypeFromDbUrlNullAndFail() {
    assertThat(DatabaseType.getTypeFromDbUrl(null)).isNull();
    assertThat(DatabaseType.getTypeFromDbUrl("asdasdasd")).isNull();
  }

  @Test
  void getTypeFromDbUrlForSimple() {
    for (DatabaseType value : DatabaseType.values()) {
      assertThat(DatabaseType.getTypeFromDbUrl(value.getGetDatabaseUrlSimple())).isEqualTo(value);
    }
  }
}
