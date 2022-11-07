package ru.pnm.dbmanger.service.commandline;

import liquibase.pro.packaged.E;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.ApplicationArguments;
import ru.pnm.dbmanger.model.commandline.CommandLineOperation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CommandlineArgServiceImplTest {

  private CommandlineArgService service;

  @Mock
  private ApplicationArguments applicationArguments;

  @BeforeEach
  void setUp() {
    service = new CommandlineArgServiceImpl(applicationArguments);
  }

  @Test
  void getCommandLineOperationFull() {
    List<String> operations = new ArrayList<>();
    operations.addAll(Arrays.stream(CommandLineOperation.values())
            .map(CommandLineOperation::getFullOperationName)
            .collect(Collectors.toList()));

    operations.add("--absentOperation1");
    operations.add("--absentOperation2");
    Mockito.when(applicationArguments.getSourceArgs()).thenReturn(operations.toArray(new String[0]));
    assertThat(service.getCommandLineArg())
        .matches(x-> Arrays.asList(CommandLineOperation.values()).containsAll(x.operations()),
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
        .matches(x-> Arrays.asList(CommandLineOperation.values()).containsAll(x.operations()),
            "has all operation");
  }

  @Test
  void getCommandLineOperationNullAndEmpty() {
    String[] operations = new String[0];
    Mockito.when(applicationArguments.getSourceArgs()).thenReturn(null);
    assertThat(service.getCommandLineArg())
        .matches(x-> x.operations().isEmpty(),"has empty operation");
    Mockito.when(applicationArguments.getSourceArgs()).thenReturn(operations);
    assertThat(service.getCommandLineArg())
        .matches(x-> x.operations().isEmpty(),"has empty operation");
  }

}
