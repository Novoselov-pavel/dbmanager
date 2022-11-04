package ru.pnm.dbmanger.service.commandline;

import ru.pnm.dbmanger.model.CommandLineArgs;

/**
 * Получает настройки из аргументов командной строки
 *
 * @author Новоселов Павел
 */
public interface CommandlineArgService {

  CommandLineArgs getCommandLinaArg();

}
