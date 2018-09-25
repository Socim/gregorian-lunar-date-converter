package com.keeley.lunarconverter.controllers;

import com.keeley.lunarconverter.service.DateConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
public class BirthdayController {

  @Autowired
  DateConverterService dateConverterService;

  @GetMapping(value = "/birthday/{month}/{day}")
  public List<String> calculateBirthday(@PathVariable String month,
                                        @PathVariable int day) {
    return dateConverterService.getGregorianBirthdays(month, day);
  }

  @GetMapping(value = "/ical/birthday/{month}/{day}")
  @ResponseBody
  public void calculateBirthdayICalendar(@PathVariable String month,
                                         @PathVariable int day,
                                         @RequestParam String birthdayPerson,
                                         HttpServletResponse response)
      throws IOException {

    String iCalendarFileContents = dateConverterService.getGregorianBirthdaysICalendar(month, day, birthdayPerson);

    response.setContentType("text/calendar");
    response.setHeader("Content-Disposition", "attachment;filename=" + getFileName(birthdayPerson));

    ServletOutputStream out = response.getOutputStream();
    out.println(iCalendarFileContents);
    out.flush();
    out.close();
  }

  protected String getFileName(String birthdayPerson) {
    return birthdayPerson + "Birthdays-" + new Date().getTime() + ".ics";
  }
}
