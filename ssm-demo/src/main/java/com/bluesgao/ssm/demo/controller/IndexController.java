package com.bluesgao.ssm.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class IndexController {
    @RequestMapping("/")
    public String hello(Model model){
        model.addAttribute("name", "gx");
        return "index";
    }
}
