package com.keeley.lunarconverter.repository.postgres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class DateRepository implements com.keeley.lunarconverter.repository.DateRepository {

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Override
  public List<Timestamp> getGregorianBirthdays(String l_month, int l_day, boolean allYears) {

    String sql =
        "SELECT dates.g_date " +
        "FROM ( " +
        "  SELECT " +
        "    ROW_NUMBER() OVER (ORDER BY g_date) row_num, " +
        "    g_date " +
        "  FROM lookup " +
        "  WHERE  " +
        "    l_day        = ? " +
        "    AND l_month  = ? " +
        "    AND g_date >= now() " +
        "  ORDER BY g_date " +
        ") dates " +
        "WHERE ? OR row_num=1";

    return jdbcTemplate.queryForList(sql, Timestamp.class,
        l_day, l_month, allYears);
  }

}
