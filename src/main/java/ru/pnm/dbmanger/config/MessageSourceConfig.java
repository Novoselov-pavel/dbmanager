package ru.pnm.dbmanger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.context.MessageSourceProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.Ordered;

import static java.util.Objects.nonNull;

/**
 * Конфигурация для интернационализации приложения
 *
 * @author Новоселов Павел
 */
@Configuration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class MessageSourceConfig {
  private static final String BASE_MESSAGE_NAME = "message/res";

  /**
   * Конфигурируем настройки
   *
   * @return MessageSourceProperties
   */
  @Bean
  public MessageSourceProperties messageSourceProperties(){
    MessageSourceProperties properties = new MessageSourceProperties();
    properties.setBasename(BASE_MESSAGE_NAME);
    return properties;
  }

  /**
   * Конфигурируем MessageSource
   * @param properties настройки
   * @return MessageSource
   */
  @Bean
  public MessageSource messageSource(@Autowired MessageSourceProperties properties){
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    fillMessageSource(messageSource, properties);
    return messageSource;
  }

  private void fillMessageSource(ResourceBundleMessageSource messageSource, MessageSourceProperties messageSourceProperties){
    messageSource.setBasename(messageSourceProperties.getBasename());
    messageSource.setDefaultEncoding(messageSourceProperties.getEncoding().name());
    messageSource.setFallbackToSystemLocale(messageSourceProperties.isFallbackToSystemLocale());
    if(nonNull(messageSourceProperties.getCacheDuration())){
      messageSource.setCacheMillis(messageSourceProperties.getCacheDuration().toMillis());
    }
    messageSource.setAlwaysUseMessageFormat(messageSourceProperties.isAlwaysUseMessageFormat());
    messageSource.setUseCodeAsDefaultMessage(messageSourceProperties.isUseCodeAsDefaultMessage());
  }
}
