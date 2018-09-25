package com.keeley.lunarconverter.controllers;

import com.keeley.lunarconverter.service.DateConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DateConversionController {

  @Autowired
  DateConverterService dateConverterService;

  @GetMapping(value = "/date/lunar/{year}/{month}/{day}")
  public String calculateBirthday(@PathVariable int year,
                                  @PathVariable int month,
                                  @PathVariable int day) {
    return dateConverterService.getLunarDate(year, month, day);
  }

}
