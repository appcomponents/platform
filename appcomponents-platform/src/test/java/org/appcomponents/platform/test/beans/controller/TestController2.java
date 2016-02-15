package org.appcomponents.platform.test.beans.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Martin Janys
 */
@Controller
@RequestMapping("/")
public class TestController2 {

    @ResponseBody
    @RequestMapping
    public String main2() {
        return "main2";
    }

    @ResponseBody
    @RequestMapping("hello")
    public String hello2() {
        return "hello2";
    }

}
