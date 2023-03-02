package ru.npn.dbmanger.service.url;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;
import ru.npn.dbmanger.model.commandline.DatabaseType;
import ru.npn.dbmanger.service.url.UrlBuilder;
import ru.npn.dbmanger.service.url.UrlBuilderImpl;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class UrlBuilderImplTest {

  private UrlBuilder service;
  @BeforeEach
  void setUp() {
    service = new UrlBuilderImpl();
  }

  @Test
  void getUrlNullDb() {
    assertThat(service.getUrl(new CommandLineArgs(Collections.emptySet(),
        DatabaseType.POSTGRES.getGetDatabaseUrlSimple(),
        null,
        null,
        null,
        null,
        "test",
        null,
        null,
        null,
        Collections.emptyMap()))).isEqualTo(DatabaseType.POSTGRES.getGetDatabaseUrlSimple() + "test");
  }

  @Test
  void getUrlDbTypePostgres() {
    assertThat(service.getUrl(new CommandLineArgs(Collections.emptySet(),
        DatabaseType.POSTGRES.getGetDatabaseUrlSimple(),
        null,
        null,
        null,
        null,
        "test",
        null,
        DatabaseType.POSTGRES,
        null,
        Collections.emptyMap()))).isEqualTo(DatabaseType.POSTGRES.getGetDatabaseUrlSimple() + "test");
  }
}
