package com.keeley.lunarconverter.controllers;

import com.keeley.lunarconverter.domain.LunarDate;
import com.keeley.lunarconverter.service.DateConverterService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Controller
public class CalendarController {

  @Autowired
  DateConverterService dateConverterService;

  @RequestMapping("/")
  public String view(Map<String, Object> model) {

    LocalDateTime dt = LocalDateTime.now();
    LunarDate lunarDate = dateConverterService.getLunarDate(dt.getYear(), dt.getMonth().getValue(), dt.getDayOfMonth());

    model.put("lday", lunarDate.getDay());
    model.put("lmonth", lunarDate.getMonth());
    model.put("lyear", lunarDate.getYear());

    return "index";
  }
}
