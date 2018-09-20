package com.keeley.lunarconverter.service;

import com.keeley.lunarconverter.repository.postgres.DateRepository;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(JMockit.class)
public class UT_DateConverterService {
  private static long DATE_MILLIS = 1522213200000L; // 3.28.2018

  @Injectable
  private DateRepository dateRepository;
  //@Injectable
  //private RandomUidGenerator randomUidGenerator;
  @Tested
  private DateConverterService service; // = new DateConverterService();

  @Test
  public void createICalFromTimestamps(){
//    new Expectations() {{
//      randomUidGenerator.generateUid();
//      result = new Uid("1A");
//    }};

//    List<Timestamp> timestamps = Arrays.asList(new Timestamp(DATE_MILLIS));
//
//    Calendar calendar = service.createICalFromTimestamps(timestamps, "Tony");
//
//    assertNotNull(calendar);
  }

}
