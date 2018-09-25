package com.keeley.lunarconverter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class CalendarController {
  @RequestMapping("/")
  public String view(Map<String, Object> model) {
    return "index";
  }
}
