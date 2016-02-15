package org.appcomponents.platform.test.beans.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Martin Janys
 */
@Controller
@RequestMapping("/")
public class TestController {

    @ResponseBody
    @RequestMapping
    public String main() {
        return "main";
    }

    @ResponseBody
    @RequestMapping("hello")
    public String hello() {
        return "hello";
    }

}
