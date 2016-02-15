package org.appcomponents.platform.test.beans.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Martin Janys
 */
@RestController
@RequestMapping("/")
public class EchoController {

    @RequestMapping
    public String index() {
        return "index";
    }

    @RequestMapping("/echo")
    public String echo() {
        return "echo";
    }
}
