package com.jeff.study.reactive.payment;


import com.jeff.study.reactive.domain.Credit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class CreditClient {

  @Value("${api.base-url}")
  private String baseUrl;

  public Credit getCredit(String username) {
    return WebClient
            .create(baseUrl)
            .get().uri("/credit").retrieve().bodyToMono(Credit.class).block();
  }

  public CompletableFuture<Credit> getCreditFuture(String username) {
    log.debug("credit future called!");
    return CompletableFuture.supplyAsync(() -> getCredit(username));
  }
}
