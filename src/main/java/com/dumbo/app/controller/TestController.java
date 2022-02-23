package com.dumbo.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
   @GetMapping("/init")
    public String initCon(){
       return "dumbo platfrom init";
   }
}
