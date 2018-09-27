package com.keeley.lunarconverter.service;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.property.Summary;
import biweekly.util.Duration;
import com.keeley.lunarconverter.domain.LunarDate;
import com.keeley.lunarconverter.repository.postgres.DateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DateConverterService {

  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");
  private static final String EVENT_SUFFIX = "'s Birthday";

  @Autowired
  private DateRepository dateRepository;

  public LunarDate getLunarDate(int year, int month, int day) {
    return dateRepository.getLunarDateFromGregorian(createTimestamp(year, month, day));
  }

  public String getGregorianDate(int year, String month, int day) {
    Timestamp timestamp = dateRepository.getGregorianDateFromLunar(year, month, day);
    return convertTimestampToDateString(timestamp);
  }

  public List<String> getGregorianBirthdays(String month, int day) {
    Collection<Timestamp> timestamps = getBirthdays(month, day, false);
    return timestamps.stream()
        .map(this::convertTimestampToDateString)
        .collect(Collectors.toList());
  }

  public String getGregorianBirthdaysICalendar(String month, int day, String birthdayPerson) {
    Collection<Timestamp> timestamps = getBirthdays(month, day, true);
    return Biweekly.write(createICalendar(timestamps, birthdayPerson)).go();
  }

  protected Collection<Timestamp> getBirthdays(String month, int day, boolean allYears) {
    if (isLeapMonth(month)) {
      return dateRepository.getGregorianBirthdaysBornLeapMonth(month, day, allYears);
    } else if (day == 30) {
      return dateRepository.getGregorianBirthdaysBornThirtieth(month, day, allYears);
    } else {
      return dateRepository.getGregorianBirthdays(month, day, allYears);
    }
  }

  protected boolean isLeapMonth(String month) {
    return month.contains("L");
  }

  protected ICalendar createICalendar(Collection<Timestamp> timestamps, String birthdayPerson) {
    ICalendar ical = new ICalendar();
    timestamps.forEach((t) -> ical.addEvent(createEventForBirthday(t, birthdayPerson)));
    return ical;
  }

  protected VEvent createEventForBirthday(Timestamp timestamp, String birthdayPerson) {
    VEvent event = new VEvent();
    Summary summary = event.setSummary(birthdayPerson + EVENT_SUFFIX);
    summary.setLanguage("en-us");

    event.setDateStart(new Date(timestamp.getTime()), false);

    Duration duration = new Duration.Builder().days(1).build();
    event.setDuration(duration);

    return event;
  }

  protected String convertTimestampToDateString(Timestamp timestamp) {
    if (timestamp == null) {
      return "";
    }

    LocalDate localDate = timestamp.toLocalDateTime().toLocalDate();
    return DATE_TIME_FORMATTER.format(localDate);
  }

  protected Timestamp createTimestamp(int year, int month, int day) {
    return new Timestamp(year-1900, month-1, day, 0, 0 ,0, 0);
  }

}
