package ru.pnm.dbmanger.service.commandline;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;
import ru.pnm.dbmanger.model.CommandLineArgs;

import java.util.Arrays;

/**
 * Получает настройки из аргументов командной строки
 *
 * @author Новоселов Павел
 */
@Service
@RequiredArgsConstructor
public class CommandlineArgServiceImpl implements CommandlineArgService {

  private final ApplicationArguments applicationArguments;

  @Override
  public CommandLineArgs getCommandLinaArg() {
    System.out.println("getSourceArgs()");
    Arrays.stream(applicationArguments.getSourceArgs()).forEach(x-> System.out.println(x));

    System.out.println("getOptionNames()");
    applicationArguments.getOptionNames().forEach(x-> System.out.println(x));
    System.out.println("getNonOptionArgs()");
    applicationArguments.getNonOptionArgs().forEach(x-> System.out.println(x));
    return null;
  }
}
