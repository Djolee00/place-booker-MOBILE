package com.placebooker.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class XSSTestController {
    @PostMapping("/test1")
    public Object test(@RequestBody Object re) throws JsonProcessingException {
        // Prevent with Xss filter for body request
        System.out.println(new ObjectMapper().writeValueAsString(re));
        return re;
    }

    @GetMapping("/test2")
    public String testUn(@RequestParam String param) {
        // Prevent with Xss filter for parameters
        System.out.println(param);
        return param;
    }

    @GetMapping("/test3")
    public String testReturn() {
        // return a execution script if spring security configuration headers are disabled
        return """
      {
    "id": 1,
    "name": "<script>alert('Hello >:)!')</script>"
    }
      """;
    }
}
