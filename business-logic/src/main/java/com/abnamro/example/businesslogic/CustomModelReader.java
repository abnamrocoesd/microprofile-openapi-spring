package com.abnamro.example.businesslogic;

import org.eclipse.microprofile.openapi.OASFactory;
import org.eclipse.microprofile.openapi.OASModelReader;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.Operation;
import org.eclipse.microprofile.openapi.models.PathItem;

public class CustomModelReader implements OASModelReader {

    @Override
    public OpenAPI buildModel() {
        return OASFactory.createObject(OpenAPI.class)
                .path("/api/fake/read", OASFactory.createObject(PathItem.class)
                        .GET(OASFactory.createObject(Operation.class)
                                .operationId("fake read resource")));
    }
}
