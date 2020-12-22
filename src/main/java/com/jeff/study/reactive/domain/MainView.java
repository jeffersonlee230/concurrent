package com.jeff.study.reactive.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MainView {

  private User user;
  private Credit credit;
  private History history;
  private Asset asset;

  @Builder
  public MainView(User user, Credit credit, History history, Asset asset) {
    this.user = user;
    this.credit = credit;
    this.history = history;
    this.asset = asset;
  }
}
