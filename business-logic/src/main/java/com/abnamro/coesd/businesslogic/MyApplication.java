package com.abnamro.coesd.businesslogic;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(info = @Info(termsOfService = "Commercial", title = "ABN-AMRO services", version = "2.2"))
public class MyApplication extends Application {
}