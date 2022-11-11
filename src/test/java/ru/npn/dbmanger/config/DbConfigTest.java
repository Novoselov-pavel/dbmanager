package ru.npn.dbmanger.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import ru.npn.dbmanger.TestDataBuilder;
import ru.npn.dbmanger.exception.CommandLineArgValidationException;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;
import ru.npn.dbmanger.service.commandline.validator.CommandlineArgValidator;
import ru.npn.dbmanger.service.hikari.config.HikariConfigFactory;
import ru.npn.dbmanger.service.hikari.config.HikariConfigFactoryImpl;
import ru.npn.dbmanger.service.message.MessageService;
import ru.npn.dbmanger.service.url.UrlBuilderImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
class DbConfigTest {

  private DbConfig service;

  @BeforeEach
  void setUp() {
    service = new DbConfig();
  }

  @Test
  void dataSourceForCommonOperationFailValidate() throws CommandLineArgValidationException {
    CommandLineArgs args = TestDataBuilder.argsWithCreateDbSchemaCommonOperation();
    CommandlineArgValidator validator = Mockito.mock(CommandlineArgValidator.class);
    HikariConfigFactory hikariConfigFactory = Mockito.mock(HikariConfigFactory.class);
    MessageService messageService = Mockito.mock(MessageService.class);
    Mockito.doThrow(CommandLineArgValidationException.class).when(validator).validate(args);
    assertThat(service.dataSourceForCommonOperation(args,validator,hikariConfigFactory, messageService))
        .isNull();
  }

  @Test()
  void dataSourceForCommonOperationGoodValidate() {
    CommandLineArgs args = TestDataBuilder.argsWithCreateDbSchemaCommonOperation();
    CommandlineArgValidator validator = Mockito.mock(CommandlineArgValidator.class);
    HikariConfigFactory hikariConfigFactory = new HikariConfigFactoryImpl(new UrlBuilderImpl());
    MessageService messageService = Mockito.mock(MessageService.class);
    assertThrows(Exception.class,()->service.dataSourceForCommonOperation(args,validator,hikariConfigFactory, messageService));
  }
}
