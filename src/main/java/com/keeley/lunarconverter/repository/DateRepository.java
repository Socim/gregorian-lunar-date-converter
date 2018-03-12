package com.keeley.lunarconverter.repository;

import java.sql.Timestamp;
import java.util.List;

public interface DateRepository {
  List<Timestamp> getGregorianBirthdays(int l_month, int l_day, boolean allYears);
}
