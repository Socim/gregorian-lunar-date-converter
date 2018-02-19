package com.keeley.lunarconverter.dao;

import com.keeley.lunarconverter.domain.GregorianDate;
import com.keeley.lunarconverter.domain.LunarDate;

public interface DateConverterDao {
  public GregorianDate get(LunarDate lunarDate);
}
