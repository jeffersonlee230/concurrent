package com.jeff.study.reactive.payment;

import com.jeff.study.reactive.domain.Asset;
import com.jeff.study.reactive.domain.Credit;
import com.jeff.study.reactive.domain.History;
import com.jeff.study.reactive.domain.MainView;
import com.jeff.study.reactive.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class MainService {

  private final UserClient userClient;
  private final CreditClient creditClient;
  private final AssetClient assetClient;
  private final HistoryClient historyClient;

  public MainView getMain(String userId) {
    log.debug("start api call");
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    User user = userClient.getUser(userId);
    log.debug("user:{}", user);
    Credit credit = creditClient.getCredit(user.getUsername());
    log.debug("credit:{}", credit);
    Asset asset = assetClient.getAsset(user.getUsername());
    log.debug("asset:{}", asset);
    History history = historyClient.getHistory(user.getUsername());
    log.debug("history:{}", history);

    stopWatch.stop();
    log.debug("total-seconds:{}", stopWatch.getTotalTimeSeconds());
    return MainView.builder().user(user).credit(credit).asset(asset).history(history).build();
  }

  @SneakyThrows
  public MainView getMainByCompletableFuture2(String userId) {
    log.debug("start future api call");
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    MainView.MainViewBuilder builder = MainView.builder();

    User user = userClient.getUser(userId);
    String username = user.getUsername();
    builder.user(user);

    CompletableFuture.allOf(
            CompletableFuture.supplyAsync(() -> assetClient.getAsset(username)).thenAccept(builder::asset).thenRun(() -> {
              log.debug("asset called!");
            }),
            CompletableFuture.supplyAsync(() -> creditClient.getCredit(username)).thenAccept(builder::credit).thenRun(() -> {
              log.debug("credit called!");
            }),
            CompletableFuture.supplyAsync(() -> historyClient.getHistory(username)).thenAccept(builder::history).thenRun(() -> {
              log.debug("history called!");
            })
    ).join();

    stopWatch.stop();
    log.debug("total-seconds:{}", stopWatch.getTotalTimeSeconds());
    return builder.build();
  }

  @SneakyThrows
  public MainView getMainByCompletableFuture(String userId) {
    log.debug("start future api call");
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    MainView.MainViewBuilder builder = MainView.builder();

    userClient.getUserFuture(userId)
            .thenCompose(u -> {
              builder.user(u);
              return getListCompletableFuture(u.getUsername());
            })
            .get()
            .forEach(obj -> {
              if (obj instanceof Credit) {
                System.out.println("Credit:" + obj);
                builder.credit((Credit) obj);
              } else if (obj instanceof Asset) {
                System.out.println("Asset:" + obj);
                builder.asset((Asset) obj);
              } else if (obj instanceof History) {
                System.out.println("History:" + obj);
                builder.history((History) obj);
              }
            });


    stopWatch.stop();
    log.debug("total-seconds:{}", stopWatch.getTotalTimeSeconds());
    return builder.build();
  }

  private CompletableFuture<List<Object>> getListCompletableFuture(String username) {
    List<CompletableFuture> futures = Arrays.asList(creditClient.getCreditFuture(username),
            assetClient.getAssetFuture(username), historyClient.getHistoryFuture(username));
    CompletableFuture[] futuresArray = futures.toArray(new CompletableFuture[futures.size()]);
    CompletableFuture<List<Object>> future = CompletableFuture.allOf(futuresArray).thenApply(v -> {
      return futures.stream().map(f -> f.join()).collect(Collectors.toList());
    });
    return future;
  }

}
