package ru.npn.dbmanger.service.url;

import org.springframework.stereotype.Service;
import ru.npn.dbmanger.model.commandline.CommandLineArgs;

import java.util.Objects;

/**
 * @author Новоселов Павел
 */
@Service
public class UrlBuilderImpl implements UrlBuilder {
  @Override
  public String getUrl(CommandLineArgs args) {
    if(Objects.isNull(args.databaseType())){
      return args.dbUrl() + args.dbName();
    }
    return switch (args.databaseType()){
      case POSTGRES -> args.dbUrl() + args.dbName();
      default -> args.dbUrl() + args.dbName();
    };
  }
}
