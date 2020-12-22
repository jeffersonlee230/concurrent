package com.jeff.study.reactive.payment;

import com.jeff.study.reactive.TestUtils;
import com.jeff.study.reactive.domain.MainView;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@Slf4j
@SpringBootTest
class MainServiceTest {

  @Autowired
  private MainService mainService;

  @Test
  @DisplayName("순차실행")
  void getMain() {

    MainView mainView = mainService.getMain("1");
    assertConditions(mainView);
  }

  @Test
  @DisplayName("CompletableFuture")
  void getMainByCompletableFuture() {

    MainView mainView = mainService.getMainByCompletableFuture("1");
    assertConditions(mainView);
  }

  @Test
  @DisplayName("CompletableFuture2")
  void getMainByCompletableFuture2() {

    MainView mainView = mainService.getMainByCompletableFuture2("1");
    assertConditions(mainView);
  }

  private void assertConditions(MainView mainView) {
    assertNotNull(mainView.getUser());
    assertEquals(mainView.getUser().getUsername(), "focusinkiller@gmail.com");
    log.debug("mainView:" + TestUtils.toJson(mainView));
  }
}