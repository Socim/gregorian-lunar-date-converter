package com.keeley.lunarconverter;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CalendarController {
  @RequestMapping("/")
  public String view(Map<String, Object> model) {
    // TODO: today's lunar date?
    return "index";
  }
}
