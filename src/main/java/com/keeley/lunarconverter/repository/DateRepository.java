package com.keeley.lunarconverter.repository;

import com.keeley.lunarconverter.domain.LunarDate;

import java.sql.Timestamp;
import java.util.List;

public interface DateRepository {
  List<Timestamp> getGregorianBirthdays(String l_month, int l_day, boolean allYears);

  LunarDate getLunarDateFromGregorian(Timestamp timestamp);
}
