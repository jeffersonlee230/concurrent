package com.jeff.study.reactive.payment;


import com.jeff.study.reactive.domain.Credit;
import com.jeff.study.reactive.domain.History;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;


@Slf4j
@Component
public class HistoryClient {


  @Value("${api.base-url}")
  private String baseUrl;

  public History getHistory(String username) {
    return WebClient
            .create(baseUrl)
            .get().uri("/history").retrieve().bodyToMono(History.class).block();
  }

  public CompletableFuture<History> getHistoryFuture(String username) {
    log.debug("history future called!");
    return CompletableFuture.supplyAsync(() -> getHistory(username));
  }

}
