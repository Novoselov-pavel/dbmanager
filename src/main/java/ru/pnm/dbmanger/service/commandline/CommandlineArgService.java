package ru.pnm.dbmanger.service.commandline;

import ru.pnm.dbmanger.model.commandline.CommandLineArgs;

/**
 * Получает настройки из аргументов командной строки
 *
 * @author Новоселов Павел
 */
public interface CommandlineArgService {

  CommandLineArgs getCommandLineArg();

}
