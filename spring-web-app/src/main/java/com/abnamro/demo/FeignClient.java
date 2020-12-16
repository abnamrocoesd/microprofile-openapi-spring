package com.abnamro.demo;

import org.springframework.web.bind.annotation.GetMapping;

//@FeignClient
public interface FeignClient {

    @GetMapping("/bogus")
    String getMyValue();
}
