package ru.npn.dbmanger.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.npn.dbmanger.TestDataBuilder;
import ru.npn.dbmanger.exception.CommandLineArgValidationException;
import ru.npn.dbmanger.exception.DbUpdateException;
import ru.npn.dbmanger.service.commandline.CommandlineArgService;
import ru.npn.dbmanger.service.commandline.validator.CommandlineArgValidator;
import ru.npn.dbmanger.service.db.DbProcessService;
import ru.npn.dbmanger.service.db.operation.CommonOperationFactory;
import ru.npn.dbmanger.service.db.operation.CommonOperationProvider;
import ru.npn.dbmanger.service.help.HelpService;
import ru.npn.dbmanger.service.hikari.config.HikariConfigFactory;
import ru.npn.dbmanger.service.message.MessageService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DbManagerServiceImplTest {


  @Mock
  private DbProcessService dbProcessService;
  @Mock
  private HelpService helpService;
  @Mock
  private CommandlineArgService commandlineArgService;
  @Mock
  private CommandlineArgValidator validator;
  @Mock
  private HikariConfigFactory hikariConfigFactory;
  @Mock
  private MessageService messageService;
  @Mock
  private List<CommonOperationProvider> operationsProviders;
  @Mock
  private CommonOperationFactory commonOperationFactory;

  private DbManagerService service;

  @BeforeEach
  void setUp() {
    service = new DbManagerServiceImpl(dbProcessService, helpService, commandlineArgService, validator, hikariConfigFactory, messageService, operationsProviders,
        commonOperationFactory);
  }

  @Test
  void executeWithValidatorException() throws CommandLineArgValidationException {
    Mockito.when(helpService.process(Mockito.any())).thenReturn(false);
    Mockito.doThrow(new CommandLineArgValidationException(Collections.emptyList())).when(validator).validate(Mockito.any());
    assertThrows(DbUpdateException.class, ()-> service.execute());
    Mockito.verify(messageService,Mockito.times(1)).log(Mockito.any(CommandLineArgValidationException.class));
  }



  @Test
  void executeWithError() throws CommandLineArgValidationException {
    Mockito.when(commandlineArgService.getCommandLineArg()).thenReturn(TestDataBuilder.argsWithCreateDbSchemaCommonOperation());

    Mockito.when(helpService.process(Mockito.any())).thenReturn(false);
    Mockito.when(dbProcessService.process(Mockito.any(), Mockito.any())).thenReturn(false);
    assertThrows(DbUpdateException.class, ()-> service.execute());
    Mockito.verify(messageService,Mockito.times(0)).log(Mockito.any(CommandLineArgValidationException.class));

  }



  @Test
  void executeWithoutError() throws CommandLineArgValidationException {
    Mockito.when(commandlineArgService.getCommandLineArg()).thenReturn(TestDataBuilder.argsWithCreateDbSchemaCommonOperation());
    Mockito.when(helpService.process(Mockito.any())).thenReturn(false);
    Mockito.when(dbProcessService.process(Mockito.any(), Mockito.any())).thenReturn(true);
    service.execute();
    Mockito.verify(messageService,Mockito.times(0)).log(Mockito.any(CommandLineArgValidationException.class));
  }
}
