package com.abnamro.example.businesslogic;

import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.ws.rs.HeaderParam;

public interface HelloControllerIntf {

    @GetMapping("/springboot")
    @Parameter(name = "X-Custom-Header", in = ParameterIn.HEADER, required = true)
    @APIResponse(responseCode = "400", description = "Description 400")
    String getGreeting(@RequestHeader("X-Custom-Header") @HeaderParam("X-Custom-Header") String string);
}
