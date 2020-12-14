package com.abnamro.coesd.springboot.openapi;

import io.smallrye.openapi.api.OpenApiDocument;
import io.smallrye.openapi.runtime.io.Format;
import io.smallrye.openapi.runtime.io.OpenApiSerializer;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class OpenController {

    @Autowired
    OpenApiDocument doc;

    @Operation(hidden = true)
    @GetMapping("/openapi")
    public String openapi() throws IOException {
        return write(doc);
    }

    private String write(OpenApiDocument schema) throws IOException {
        schema.initialize();
        return OpenApiSerializer.serialize(schema.get(), Format.YAML);
    }

}


