package com.keeley.lunarconverter.service;

import com.keeley.lunarconverter.dao.postgres.DateConverterDao;
import com.keeley.lunarconverter.domain.GregorianDate;
import com.keeley.lunarconverter.domain.LunarDate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class DateConverterService {

  @Autowired
  DateConverterDao dateConverterDao;

  public GregorianDate getGregorianDate(LunarDate lunarDate) {
    return dateConverterDao.get(lunarDate);
  }

  public Set<GregorianDate> getGregorianDate(Set<LunarDate> lunarDates) {
    // TODO: implement
    
    return null;
  }

}
