package com.keeley.lunarconverter.repository;

import com.keeley.lunarconverter.domain.LunarDate;

import java.sql.Timestamp;
import java.util.Collection;

public interface DateRepository {
  Collection<Timestamp> getGregorianBirthdays(String l_month, int l_day, boolean allYears);

  Collection<Timestamp> getGregorianBirthdaysBornLeapMonth(String l_month, int l_day, boolean allYears);

  Collection<Timestamp> getGregorianBirthdaysBornThirtieth(String l_month, int l_day, boolean allYears);

  LunarDate getLunarDateFromGregorian(Timestamp timestamp);

  Timestamp getGregorianDateFromLunar(int year, String month, int day);
}
