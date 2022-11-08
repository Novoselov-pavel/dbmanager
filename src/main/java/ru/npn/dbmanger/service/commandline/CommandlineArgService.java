package ru.npn.dbmanger.service.commandline;

import ru.npn.dbmanger.model.commandline.CommandLineArgs;

/**
 * Получает настройки из аргументов командной строки
 *
 * @author Новоселов Павел
 */
public interface CommandlineArgService {

  CommandLineArgs getCommandLineArg();

}
