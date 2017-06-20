package com.kappa.web.hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
class HelloWorldController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    String get() {
        return "Hello, World!";
    }
}