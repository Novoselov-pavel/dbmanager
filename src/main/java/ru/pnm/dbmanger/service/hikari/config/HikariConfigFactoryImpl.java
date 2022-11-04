package ru.pnm.dbmanger.service.hikari.config;

import com.zaxxer.hikari.HikariConfig;
import org.springframework.stereotype.Service;
import ru.pnm.dbmanger.model.CommandLineArgs;

/**
 * @author Новоселов Павел
 */
@Service
public class HikariConfigFactoryImpl implements HikariConfigFactory {
  @Override
  public HikariConfig getConfig(CommandLineArgs args) {
    return null;
  }
}
