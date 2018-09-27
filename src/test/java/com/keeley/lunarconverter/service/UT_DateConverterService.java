package com.keeley.lunarconverter.service;

import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.*;

public class UT_DateConverterService {
  private static long DATE_MILLIS = 1522213200000L; // 3.28.2018

  private DateConverterService service = new DateConverterService();

  @Test
  public void createTimestamp() {
    Timestamp expected = new Timestamp(DATE_MILLIS);
    assertEquals(expected, service.createTimestamp(2018, 3, 28));
  }

  @Test
  public void convertTimestampToDateString_null() {
    assertEquals("", service.convertTimestampToDateString(null));
  }

  @Test
  public void convertTimestampToDateString() {
    assertEquals("Wednesday, March 28, 2018", service.convertTimestampToDateString(new Timestamp(DATE_MILLIS)));
  }

  @Test
  public void isLeapMonth() {
    assertTrue(service.isLeapMonth("4L"));
  }

  @Test
  public void isLeapMonth_nope() {
    assertFalse(service.isLeapMonth("4"));
  }

}
