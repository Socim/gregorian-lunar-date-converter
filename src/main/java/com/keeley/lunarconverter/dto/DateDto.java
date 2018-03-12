package com.keeley.lunarconverter.dto;

public class DateDto {
  private int year;
  private int month;
  private int day;

  public int getYear() {
    return year;
  }

  public int getMonth() {
    return month;
  }

  public int getDay() {
    return day;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public void setMonth(int month) {
    this.month = month;
  }

  public void setDay(int day) {
    this.day = day;
  }

  @Override
  public String toString() {
    return "DateDto{" +
        "year='" + String.valueOf(year) + '\'' +
        ", month='" + String.valueOf(month) + '\'' +
        ", day='" + String.valueOf(day) + '\'' +
        '}';
  }
}
