package com.keeley.lunarconverter;

import com.keeley.lunarconverter.dto.DateDto;
import com.keeley.lunarconverter.service.DateConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BirthdayController {

  @Autowired
  DateConverterService dateConverterService;

  @GetMapping(value = "/birthday")
  public List<String> calculateBirthday(
      DateDto lunarDateDto,
      @RequestParam(defaultValue = "false", required = false) boolean allYears) {

    return dateConverterService.getGregorianBirthdays(lunarDateDto, allYears);
  }

}
