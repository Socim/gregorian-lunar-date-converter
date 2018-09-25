package com.keeley.lunarconverter.repository;

import com.keeley.lunarconverter.domain.LunarDate;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LunarDateExtractor implements ResultSetExtractor<LunarDate> {
  @Override
  public LunarDate extractData(ResultSet rs) throws SQLException, DataAccessException {

    if (rs.next()) {
      return new LunarDate(
          rs.getInt("l_year"),
          rs.getString("l_month"),
          rs.getInt("l_day"));
    }

    return null;
  }
}
