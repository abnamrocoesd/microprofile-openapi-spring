package com.abnamro.openapi.springboot;

import io.smallrye.openapi.api.OpenApiConfig;
import io.smallrye.openapi.api.OpenApiDocument;
import io.smallrye.openapi.runtime.OpenApiProcessor;
import io.smallrye.openapi.runtime.OpenApiStaticFile;
import io.smallrye.openapi.runtime.io.Format;
import io.smallrye.openapi.runtime.scanner.OpenApiAnnotationScanner;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.jboss.jandex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Configuration
@ComponentScan(value = "com.abnamro.openapi.springboot")
public class OpenApiDocumentProvider {

    private static final String META_INF_OPENAPI_YAML = "META-INF/openapi.yaml";
    private static final String WEB_INF_CLASSES_META_INF_OPENAPI_YAML = "WEB-INF/classes/META-INF/openapi.yaml";
    private static final String META_INF_OPENAPI_YML = "META-INF/openapi.yml";
    private static final String WEB_INF_CLASSES_META_INF_OPENAPI_YML = "WEB-INF/classes/META-INF/openapi.yml";
    private static final String META_INF_OPENAPI_JSON = "META-INF/openapi.json";
    private static final String WEB_INF_CLASSES_META_INF_OPENAPI_JSON = "WEB-INF/classes/META-INF/openapi.json";

    @Autowired
    private OpenApiConfig openApiConfig;

    @Value("${mp.openapi.extensions.abnamro.classpath:com/abnamro/**}")
    private String classpath;

    @Bean
    public OpenApiDocument openApiDocument() throws IOException {
        Index index = indexMatchingClasses("classpath*:"+classpath)
                        .complete();

        OpenAPI staticModel = generateStaticModel();

        ClassLoader classLoader = this.getClass().getClassLoader();
        OpenAPI readerModel = OpenApiProcessor.modelFromReader(openApiConfig, classLoader);

        OpenApiAnnotationScanner openApiAnnotationScanner = new OpenApiAnnotationScanner(openApiConfig, index);
        OpenAPI annotationModel = openApiAnnotationScanner.scan();

        OpenApiDocument document = OpenApiDocument.INSTANCE;

        document.reset();
        document.config(openApiConfig);

        if (annotationModel != null) {
            document.modelFromAnnotations(annotationModel);
        }
        if (readerModel != null) {
            document.modelFromReader(readerModel);
        }
        if (staticModel != null) {
            document.modelFromStaticFile(staticModel);
        }

        return document;
    }

    private OpenAPI generateStaticModel() throws IOException {
        Path staticFile = getStaticFile();
        if (staticFile != null) {
            try (InputStream is = Files.newInputStream(staticFile);
                 OpenApiStaticFile openApiStaticFile = new OpenApiStaticFile(is, getFormat(staticFile))) {
                return OpenApiProcessor.modelFromStaticFile(openApiStaticFile);
            }
        }
        return null;
    }

    private Path getStaticFile() {
        List<String> paths = Arrays.asList(META_INF_OPENAPI_YAML, WEB_INF_CLASSES_META_INF_OPENAPI_YAML, META_INF_OPENAPI_YML,
                WEB_INF_CLASSES_META_INF_OPENAPI_YML, META_INF_OPENAPI_JSON, WEB_INF_CLASSES_META_INF_OPENAPI_JSON);

        for (String path : paths) {
            try {
                File file = new ClassPathResource(path).getFile();
                return file.toPath();
            } catch (IOException ex) {
                //Do nothing, iterate to next;
            }
        }

        return null;
    }

    private Format getFormat(Path path) {
        if (path.endsWith(".json")) {
            return Format.JSON;
        }
        return Format.YAML;
    }


    private static Indexer indexMatchingClasses(String matchPattern) throws IOException {
        Indexer indexer = new Indexer();
        PathMatchingResourcePatternResolver scanner = new PathMatchingResourcePatternResolver();
        Resource[] resources = scanner.getResources(matchPattern);

        for (Resource resource : resources) {
            if (resource.isReadable()) {
                indexer.index(resource.getInputStream());
            }
        }

        return indexer;
    }


}
