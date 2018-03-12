package com.keeley.lunarconverter.service;

import com.keeley.lunarconverter.dto.DateDto;
import com.keeley.lunarconverter.repository.postgres.DateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DateConverterService {

  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");

  @Autowired
  private DateRepository dateRepository;

  public List<String> getGregorianBirthdays(DateDto lunarDateDto, boolean allYears) {
    List<Timestamp> timestamps = dateRepository.getGregorianBirthdays(lunarDateDto.getMonth(), lunarDateDto.getDay(), allYears);

    return timestamps.stream()
        .map(this::convertTimestampToDateString)
        .collect(Collectors.toList());
  }

  protected String convertTimestampToDateString(Timestamp timestamp) {
    LocalDate localDate = timestamp.toLocalDateTime().toLocalDate();
    return DATE_TIME_FORMATTER.format(localDate);
  }

}
