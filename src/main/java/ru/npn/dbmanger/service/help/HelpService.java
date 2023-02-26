package ru.npn.dbmanger.service.help;

import lombok.NonNull;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;

/**
 * Служба обработки ключа командной строки получения справки
 *
 * @author Новоселов Павел
 */
public interface HelpService {

  /**
   * Запускает обработку ключа командной строки получения справки
   *
   * @param args аргументы командной строки
   * @return true если ключ получения справки присутствовал в аргументах командной строки
   * иначе false
   */
  boolean process(@NonNull CommandLineArgs args);

}
