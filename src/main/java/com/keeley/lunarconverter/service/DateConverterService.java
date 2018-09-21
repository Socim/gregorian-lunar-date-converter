package com.keeley.lunarconverter.service;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.property.Summary;
import biweekly.util.Duration;
import com.keeley.lunarconverter.repository.postgres.DateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DateConverterService {

  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");
  private static final String EVENT_SUFFIX = "'s Birthday";

  @Autowired
  private DateRepository dateRepository;

  public List<String> getGregorianBirthdays(String month, int day) {
    List<Timestamp> timestamps = dateRepository.getGregorianBirthdays(month, day, false);

    return timestamps.stream()
        .map(this::convertTimestampToDateString)
        .collect(Collectors.toList());
  }

  public String getGregorianBirthdaysICalendar(String month, int day, String birthdayPerson) {
    List<Timestamp> timestamps = dateRepository.getGregorianBirthdays(month, day, true);

    // TODO: write out to an output stream

    return Biweekly.write(createICalendar(timestamps, birthdayPerson)).go();
  }

  protected ICalendar createICalendar(List<Timestamp> timestamps, String birthdayPerson) {
    ICalendar ical = new ICalendar();
    timestamps.forEach((t) -> ical.addEvent(createEventForBirthday(t, birthdayPerson)));
    return ical;
  }

  protected VEvent createEventForBirthday(Timestamp timestamp, String birthdayPerson) {
    VEvent event = new VEvent();
    Summary summary = event.setSummary(birthdayPerson + EVENT_SUFFIX);
    summary.setLanguage("en-us");

    // TODO: timezone?

    event.setDateStart(new Date(timestamp.getTime()), false);

    Duration duration = new Duration.Builder().days(1).build();
    event.setDuration(duration);

    return event;
  }

  protected String convertTimestampToDateString(Timestamp timestamp) {
    LocalDate localDate = timestamp.toLocalDateTime().toLocalDate();
    return DATE_TIME_FORMATTER.format(localDate);
  }

}
