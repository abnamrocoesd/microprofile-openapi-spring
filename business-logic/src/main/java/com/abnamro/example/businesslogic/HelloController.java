package com.abnamro.example.businesslogic;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.springframework.web.bind.annotation.RestController;

@OpenAPIDefinition(info = @Info(termsOfService = "Commercial", title = "ABN-AMRO services", version = "2.2", license = @License(name = "Apache")))
        @RestController
public class HelloController implements HelloControllerIntf {

    @Override
    public String getGreeting(String string){
        return "hello";
    }
}
