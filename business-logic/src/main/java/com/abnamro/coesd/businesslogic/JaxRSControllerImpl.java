package com.abnamro.coesd.businesslogic;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


public class JaxRSControllerImpl implements JaxRSController {

    public String test(String header){
        return "test";
    }
}
