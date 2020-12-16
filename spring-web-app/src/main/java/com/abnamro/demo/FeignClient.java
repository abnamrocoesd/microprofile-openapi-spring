package com.abnamro.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
public interface FeignClient {

    @GetMapping("/bogus")
    String getMyValue();
}
