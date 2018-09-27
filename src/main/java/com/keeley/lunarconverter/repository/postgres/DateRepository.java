package com.keeley.lunarconverter.repository.postgres;

import com.keeley.lunarconverter.domain.LunarDate;
import com.keeley.lunarconverter.repository.GregorianBirthdayExtractorBornLeapMonth;
import com.keeley.lunarconverter.repository.GregorianBirthdayExtractorBornThirtieth;
import com.keeley.lunarconverter.repository.LunarDateExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Repository
public class DateRepository implements com.keeley.lunarconverter.repository.DateRepository {

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Override
  public Collection<Timestamp> getGregorianBirthdays(String l_month, int l_day, boolean allYears) {

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

    return jdbcTemplate.queryForList(sql, Timestamp.class, l_day, l_month, allYears);
  }

  /*
    Typically, if you are born in a leap month, you celebrate your birthday on that day in the previous
    month (ex: March instead of Leap March. Leap March follows March). But, if the leap month you were
    born in happens to be there this year, let's celebrate your birthday.
  */
  @Override
  public Collection<Timestamp> getGregorianBirthdaysBornLeapMonth(String l_month, int l_day, boolean allYears) {

    String previousMonth = l_month.substring(0, l_month.length() - 2);

    String sql =
        "SELECT dates.g_date " +
        "FROM ( " +
        "  SELECT " +
        "    ROW_NUMBER() OVER (ORDER BY g_date) row_num, " +
        "    g_date " +
        "  FROM lookup " +
        "  WHERE  " +
        "    l_day        = ? " +
        "    AND (l_month  = ? OR l_month  = ?)" +
        "    AND g_date >= now() " +
        "  ORDER BY g_date " +
        ") dates " +
        "ORDER BY g_date desc";

    return jdbcTemplate.query(sql, new Object[] {l_day, l_month, previousMonth, allYears},
        new GregorianBirthdayExtractorBornLeapMonth(allYears));
  }

  /*
    Months don't always have the same number of days, but they all have at least 29.
    So if you were born on the 30th, your birthday may be on the 29th of your birth month
    if there isn't 30 days in that month for the year in question.
  */
  @Override
  public Collection<Timestamp> getGregorianBirthdaysBornThirtieth(String l_month, int l_day, boolean allYears) {

    String sql =
        "SELECT dates.g_date, daysPerMonth.daysInMonth, dates.l_day " +
        "FROM ( " +
        "  SELECT " +
        "    ROW_NUMBER() OVER (ORDER BY g_date) row_num, " +
        "    lookup.* " +
        "  FROM lookup " +
        "  WHERE " +
        "    (l_day   = 30  " +
        "    OR l_day = 29) " +
        "    AND l_month  = ? " +
        "    AND g_date >= now() " +
        "  ORDER BY g_date " +
        ") dates " +
        "JOIN ( " +
        "  SELECT COUNT(*) AS daysInMonth, l_month, l_year " +
        "  FROM lookup " +
        "  GROUP BY l_month, l_year" +
        ") daysPerMonth " +
        "ON daysPerMonth.l_month = dates.l_month " +
        "AND daysPerMonth.l_year = dates.l_year " +
        "ORDER BY g_date";

    return jdbcTemplate.query(sql, new Object[] {l_month},
        new GregorianBirthdayExtractorBornThirtieth(allYears));
  }

  @Override
  public LunarDate getLunarDateFromGregorian(Timestamp timestamp) {
    String sql =
        "SELECT l_year, l_month, l_day " +
        "FROM lookup " +
        "WHERE g_date = ?";

    return jdbcTemplate.query(sql, new Object[] {timestamp}, new LunarDateExtractor());
  }

  @Override
  public Timestamp getGregorianDateFromLunar(int year, String month, int day) {
    String sql =
        "SELECT g_date " +
        "FROM lookup " +
        "WHERE l_year = ? AND l_month = ? AND l_day = ?";

    List<Timestamp> result = jdbcTemplate.queryForList(sql, Timestamp.class, year, month, day);

    if (result.size() > 0) {
      return result.get(0);
    }

    return null;
  }

}
