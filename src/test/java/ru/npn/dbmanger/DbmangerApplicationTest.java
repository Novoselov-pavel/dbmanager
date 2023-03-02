package ru.npn.dbmanger;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(args = "h")
class DbmangerApplicationTest {


  @Test
  void contextLoads(ApplicationContext context) {
   assertThat(context).isNotNull();
  }
}
