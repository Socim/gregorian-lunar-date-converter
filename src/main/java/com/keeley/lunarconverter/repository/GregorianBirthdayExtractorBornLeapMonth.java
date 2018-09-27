package com.keeley.lunarconverter.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class GregorianBirthdayExtractorBornLeapMonth implements ResultSetExtractor<Collection<Timestamp>> {

  private boolean allYears;

  public GregorianBirthdayExtractorBornLeapMonth(boolean allYears) {
    this.allYears = allYears;
  }

  @Override
  public Collection<Timestamp> extractData(ResultSet rs) throws SQLException, DataAccessException {
    Map<Integer, Timestamp> map = new HashMap<>();

    while(rs.next()) {
      Timestamp timestamp = rs.getTimestamp("g_date");
      map.putIfAbsent(timestamp.getYear(), timestamp);
      if (!allYears) {
        break;
      }
    }

    return map.values();
  }
}
