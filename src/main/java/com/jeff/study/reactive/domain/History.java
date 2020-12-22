package com.jeff.study.reactive.domain;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class History {

  private List<HistoryData> historyList;

  @Getter
  @Setter
  public static class HistoryData {

    private String to;
    private int amount;
    private LocalDate date;
  }

}
