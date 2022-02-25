package com.dumbo.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/visit")
public class TestController {
   @RequestMapping(value = "/init",method = RequestMethod.GET)
    public String initCon(){
       return "dumbo platfrom init";
   }
   @RequestMapping(value = "getList",method = RequestMethod.DELETE)
    public void del(){
       System.out.println("del");
   }
}
