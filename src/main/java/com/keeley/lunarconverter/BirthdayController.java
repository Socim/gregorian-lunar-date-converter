package com.keeley.lunarconverter;

import com.keeley.lunarconverter.domain.GregorianDate;
import com.keeley.lunarconverter.service.DateConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class BirthdayController {

  @Autowired
  DateConverterService dateConverterService;

  @GetMapping(value = "/birthday")
  public GregorianDate calculateBirthdays(
      @RequestParam String year,
      @RequestParam String lunarMonth,
      @RequestParam String lunarDay,
      @RequestParam(defaultValue = "false", required = false) Boolean allYears) {

    // TODO: get gregorian birthday
    // TODO: return birthday

    return null;
  }
}
