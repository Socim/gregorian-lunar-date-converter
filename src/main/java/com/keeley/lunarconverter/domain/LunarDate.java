package com.keeley.lunarconverter.domain;

public class LunarDate {
  private int year;
  private String month;
  private int day;

  public LunarDate (int year, String month, int day) {
    this.year = year;
    this.month = month;
    this.day = day;
  }

  public int getYear() {
    return year;
  }

  public String getMonth() {
    return month;
  }

  public int getDay() {
    return day;
  }

  public String toDateString() {
    return "year " + year + ", month " + month + ", day " + day;
  }
}
