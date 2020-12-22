package com.jeff.study.reactive.payment;

import com.jeff.study.reactive.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class UserClient {

  @Value("${api.base-url}")
  private String baseUrl;

  public User getUser(String id) {
    return WebClient
            .create(baseUrl)
            .get().uri("/user").retrieve().bodyToMono(User.class).block();
  }

  public CompletableFuture<User> getUserFuture(String id) {
    log.debug("user future called!");
    return CompletableFuture.supplyAsync(() -> getUser(id));
  }
}
