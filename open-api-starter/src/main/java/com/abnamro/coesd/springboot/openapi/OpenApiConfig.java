package com.abnamro.coesd.springboot.openapi;

import io.smallrye.openapi.api.OpenApiDocument;
import io.smallrye.openapi.runtime.scanner.OpenApiAnnotationScanner;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.jboss.jandex.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Configuration
@ComponentScan(value = "com.abnamro.coesd.springboot.openapi")
public class OpenApiConfig {

    @Bean
    public OpenApiDocument openApiDocument() throws IOException {
        Indexer indexer = new Indexer();
        listMatchingClasses("classpath*:com/abnamro/**", indexer);

        Index index = indexer.complete();
        SpringConfig springConfig = new SpringConfig();

        OpenApiAnnotationScanner openApiAnnotationScanner = new OpenApiAnnotationScanner(springConfig, index);
        OpenAPI openAPI = openApiAnnotationScanner.scan();

        OpenApiDocument document = OpenApiDocument.INSTANCE;

        document.reset();
        document.config(springConfig);

        document.modelFromAnnotations(openAPI);
        return document;
    }

    public static List<Class> listMatchingClasses(String matchPattern, Indexer indexer) throws IOException {
        List<Class> classes = new LinkedList<Class>();
        PathMatchingResourcePatternResolver scanner = new PathMatchingResourcePatternResolver();
        Resource[] resources = scanner.getResources(matchPattern);

        for (Resource resource : resources) {
            if (resource.isReadable()) {
                indexer.index(resource.getInputStream());
            }
        }

        return classes;
    }

}
