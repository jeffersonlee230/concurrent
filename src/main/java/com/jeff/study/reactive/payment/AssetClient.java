package com.jeff.study.reactive.payment;


import com.jeff.study.reactive.domain.Asset;
import com.jeff.study.reactive.domain.Credit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class AssetClient {


  @Value("${api.base-url}")
  private String baseUrl;

  public Asset getAsset(String username) {
    return WebClient
            .create(baseUrl)
            .get().uri("/asset").retrieve().bodyToMono(Asset.class).block();
  }

  public CompletableFuture<Asset> getAssetFuture(String username) {
    log.debug("asset future called!");
    return CompletableFuture.supplyAsync(() -> getAsset(username));
  }

}
