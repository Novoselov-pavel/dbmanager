package ru.npn.dbmanger.service.help;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;

/**
 * @author Новоселов Павел
 */
@Service
@RequiredArgsConstructor
public class HelpServiceImpl implements HelpService {
  @Override
  public boolean process(CommandLineArgs args) {
    return false;
  }
}
