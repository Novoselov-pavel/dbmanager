package ru.pnm.dbmanger.service.commandline;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.ApplicationArguments;
import ru.pnm.dbmanger.model.commandline.CommandLineOperation;
import ru.pnm.dbmanger.model.commandline.CommandLineOption;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CommandlineArgServiceImplTest {

  private CommandlineArgService service;

  private final String operationValue1 = "test1";
  private final String operationValue2 = "test2";

  @Mock
  private ApplicationArguments applicationArguments;

  @BeforeEach
  void setUp() {
    service = new CommandlineArgServiceImpl(applicationArguments);
  }

  @Test
  void getCommandLineOperationFull() {
    List<String> operations = Arrays.stream(CommandLineOperation.values())
        .map(CommandLineOperation::getFullOperationName)
        .collect(Collectors.toList());

    operations.add("--absentOperation1");
    operations.add("--absentOperation2");
    Mockito.when(applicationArguments.getSourceArgs()).thenReturn(operations.toArray(new String[0]));
    assertThat(service.getCommandLineArg())
        .matches(x -> Arrays.asList(CommandLineOperation.values()).containsAll(x.operations()),
            "has all operation");
  }

  @Test
  void getCommandLineOperationFullShort() {
    List<String> operations = Arrays.stream(CommandLineOperation.values())
        .map(CommandLineOperation::getShortOperationName)
        .collect(Collectors.toList());
    operations.add("-absentOperation1");
    operations.add("-absentOperation2");
    Mockito.when(applicationArguments.getSourceArgs()).thenReturn(operations.toArray(new String[0]));
    assertThat(service.getCommandLineArg())
        .matches(x -> Arrays.asList(CommandLineOperation.values()).containsAll(x.operations()),
            "has all operation");
  }

  @Test
  void getCommandLineOperationNullAndEmpty() {
    String[] operations = new String[0];
    Mockito.when(applicationArguments.getSourceArgs()).thenReturn(null);
    assertThat(service.getCommandLineArg())
        .matches(x -> x.operations().isEmpty(), "has empty operation");
    Mockito.when(applicationArguments.getSourceArgs()).thenReturn(operations);
    assertThat(service.getCommandLineArg())
        .matches(x -> x.operations().isEmpty(), "has empty operation");
  }


  @Test
  void getCommandLineStandardOptions() {
    Set<String> optionNames = Arrays
        .stream(CommandLineOption.values())
        .map(CommandLineOption::getOptionName)
        .collect(Collectors.toSet());

    Mockito.when(applicationArguments.getOptionNames()).thenReturn(optionNames);

    List<String> values = new ArrayList<>();
    values.add(operationValue1);
    values.add(operationValue2);
    Mockito.when(applicationArguments.getOptionValues(Mockito.anyString()))
        .thenReturn(values);

    assertThat(service.getCommandLineArg())
        .hasFieldOrPropertyWithValue("dbUrl", operationValue2)
        .hasFieldOrPropertyWithValue("adminUserName", operationValue2)
        .hasFieldOrPropertyWithValue("adminPassword", operationValue2)
        .hasFieldOrPropertyWithValue("dbUserName", operationValue2)
        .hasFieldOrPropertyWithValue("dbUserPassword", operationValue2)
        .hasFieldOrPropertyWithValue("dbName", operationValue2)
        .hasFieldOrPropertyWithValue("schema", operationValue2);
  }


  @Test
  void getCommandLineStandardOptionsUpperCase() {
    Set<String> optionNames = Arrays
        .stream(CommandLineOption.values())
        .map(x -> x.getOptionName().toUpperCase())
        .collect(Collectors.toSet());

    Mockito.when(applicationArguments.getOptionNames()).thenReturn(optionNames);

    List<String> values = new ArrayList<>();
    values.add(operationValue1);
    values.add(operationValue2);
    Mockito.when(applicationArguments.getOptionValues(Mockito.anyString()))
        .thenReturn(values);

    assertThat(service.getCommandLineArg())
        .hasFieldOrPropertyWithValue("dbUrl", operationValue2)
        .hasFieldOrPropertyWithValue("adminUserName", operationValue2)
        .hasFieldOrPropertyWithValue("adminPassword", operationValue2)
        .hasFieldOrPropertyWithValue("dbUserName", operationValue2)
        .hasFieldOrPropertyWithValue("dbUserPassword", operationValue2)
        .hasFieldOrPropertyWithValue("dbName", operationValue2)
        .hasFieldOrPropertyWithValue("schema", operationValue2);
  }


  @Test
  void getCommandLineStandardOptionsCheckParsing() {
    Set<String> optionNames = Arrays
        .stream(CommandLineOption.values())
        .map(CommandLineOption::getOptionName)
        .collect(Collectors.toSet());
    optionNames.add("--unknown-option");

    Mockito.when(applicationArguments.getOptionNames()).thenReturn(optionNames);

    Mockito.when(applicationArguments.getOptionValues(CommandLineOption.DB_URL.getOptionName()))
        .thenReturn(Collections.singletonList("dbUrl"));
    Mockito.when(applicationArguments.getOptionValues(CommandLineOption.ADMIN_USERNAME.getOptionName()))
        .thenReturn(Collections.singletonList("adminUserName"));
    Mockito.when(applicationArguments.getOptionValues(CommandLineOption.ADMIN_PASSWORD.getOptionName()))
        .thenReturn(Collections.singletonList("adminPassword"));
    Mockito.when(applicationArguments.getOptionValues(CommandLineOption.DB_USERNAME.getOptionName()))
        .thenReturn(Collections.singletonList("dbUserName"));
    Mockito.when(applicationArguments.getOptionValues(CommandLineOption.DB_USER_PASSWORD.getOptionName()))
        .thenReturn(Collections.singletonList("dbUserPassword"));
    Mockito.when(applicationArguments.getOptionValues(CommandLineOption.DB_NAME.getOptionName()))
        .thenReturn(Collections.singletonList("dbName"));
    Mockito.when(applicationArguments.getOptionValues(CommandLineOption.DB_SCHEMA.getOptionName()))
        .thenReturn(Collections.singletonList("schema"));

    assertThat(service.getCommandLineArg())
        .hasFieldOrPropertyWithValue("dbUrl", "dbUrl")
        .hasFieldOrPropertyWithValue("adminUserName", "adminUserName")
        .hasFieldOrPropertyWithValue("adminPassword", "adminPassword")
        .hasFieldOrPropertyWithValue("dbUserName", "dbUserName")
        .hasFieldOrPropertyWithValue("dbUserPassword", "dbUserPassword")
        .hasFieldOrPropertyWithValue("dbName", "dbName")
        .hasFieldOrPropertyWithValue("schema", "schema");
  }

  @Test
  void getCommandLineStandardOptionsNull() {
    String[] operations = new String[0];
    Mockito.when(applicationArguments.getOptionNames()).thenReturn(null);
    assertThat(service.getCommandLineArg())
        .matches(x -> x.operations().isEmpty(), "has empty operation");
    Mockito.when(applicationArguments.getSourceArgs()).thenReturn(operations);
    assertThat(service.getCommandLineArg())
        .matches(x -> x.operations().isEmpty(), "has empty operation");
  }

}
