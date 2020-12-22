package com.jeff.study.reactive.domain;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Asset {

  private int bank;
  private int kakaopay;
  private int car;
  private int loan;

}
