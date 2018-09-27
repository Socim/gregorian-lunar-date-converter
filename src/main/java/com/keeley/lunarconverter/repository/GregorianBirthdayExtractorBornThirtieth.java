package com.keeley.lunarconverter.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class GregorianBirthdayExtractorBornThirtieth implements ResultSetExtractor<List<Timestamp>> {

  private boolean allYears;

  public GregorianBirthdayExtractorBornThirtieth(boolean allYears) {
    this.allYears = allYears;
  }

  @Override
  public List<Timestamp> extractData(ResultSet rs) throws SQLException, DataAccessException {
    List<Timestamp> timestamps = new ArrayList<>();

    while(rs.next()) {
      // the lunar day is the last day of the month
      if (rs.getInt("l_day") == rs.getInt("daysInMonth")) {
        timestamps.add(rs.getTimestamp("g_date"));
        if (!allYears) {
          break;
        }
      }
    }

    return timestamps;
  }
}
