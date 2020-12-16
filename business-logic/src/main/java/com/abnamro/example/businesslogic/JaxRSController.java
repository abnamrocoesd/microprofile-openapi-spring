package com.abnamro.example.businesslogic;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;

@Path("/jaxrs")
public interface JaxRSController {

    @GET
    @Parameter(name = "X-Custom-Header", in = ParameterIn.HEADER, required = true)
    @APIResponse(responseCode = "400", description = "Description 400")
    String test(@HeaderParam("X-Custom-Header") String header);
}
