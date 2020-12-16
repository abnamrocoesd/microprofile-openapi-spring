package com.abnamro.openapi.springboot;

import io.smallrye.openapi.api.OpenApiConfig;

import java.util.Collections;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.microprofile.openapi.OASConfig;

import io.smallrye.openapi.api.constants.OpenApiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


/**
 * Implementation of the {@link OpenApiConfig} interface that gets config information from Spring
 */
@Configuration
public class SpringOpenApiConfig implements OpenApiConfig {

    @Autowired
    Environment env;

    @Override
    public String modelReader() {
        return env.getProperty(OASConfig.MODEL_READER);
    }

    @Override
    public String filter() {
        return env.getProperty(OASConfig.FILTER);
    }

    @Override
    public boolean scanDisable() {
        return Boolean.valueOf(env.getProperty(OASConfig.FILTER, "true"));
    }

    @Override
    public Pattern scanPackages() {
        String packages = env.getProperty(OASConfig.SCAN_PACKAGES);
        return patternOf(packages, Collections.singleton("com.abnamro"));
    }

    @Override
    public Pattern scanClasses() {
        return patternOf(env.getProperty(OASConfig.SCAN_CLASSES));
    }

    @Override
    public Pattern scanExcludePackages() {
        return patternOf(env.getProperty(OASConfig.SCAN_EXCLUDE_PACKAGES), OpenApiConstants.NEVER_SCAN_PACKAGES);
    }

    @Override
    public Pattern scanExcludeClasses() {
        return patternOf(env.getProperty(OASConfig.SCAN_EXCLUDE_CLASSES), OpenApiConstants.NEVER_SCAN_CLASSES);
    }

    @Override
    public Set<String> servers() {
        return asCsvSet(env.getProperty(OASConfig.SERVERS));
    }

    @Override
    public Set<String> pathServers(String path) {
        return asCsvSet(env.getProperty(OASConfig.SERVERS_PATH_PREFIX + path));
    }

    @Override
    public Set<String> operationServers(String operationId) {
        return asCsvSet(env.getProperty(OASConfig.SERVERS_OPERATION_PREFIX + operationId));
    }

    @Override
    public boolean scanDependenciesDisable() {
        return Boolean.valueOf(env.getProperty(OpenApiConstants.SMALLRYE_SCAN_DEPENDENCIES_DISABLE, "false"));
    }

    //SmallRye specific settings
    @Override
    public Set<String> scanDependenciesJars() {
        return asCsvSet(env.getProperty(OpenApiConstants.SMALLRYE_SCAN_DEPENDENCIES_JARS));
    }

    @Override
    public String customSchemaRegistryClass() {
        return env.getProperty(OpenApiConstants.SMALLRYE_CUSTOM_SCHEMA_REGISTRY_CLASS);
    }

    @Override
    public boolean applicationPathDisable() {
        return Boolean.valueOf(env.getProperty(OpenApiConstants.SMALLRYE_APP_PATH_DISABLE, "false"));
    }

    @Override
    public String getOpenApiVersion() {
        return env.getProperty(OpenApiConstants.VERSION);
    }

    @Override
    public String getInfoTitle() {
        return env.getProperty(OpenApiConstants.INFO_TITLE);
    }

    @Override
    public String getInfoVersion() {
        return env.getProperty(OpenApiConstants.INFO_VERSION);
    }

    @Override
    public String getInfoDescription() {
        return env.getProperty(OpenApiConstants.INFO_DESCRIPTION);
    }

    @Override
    public String getInfoTermsOfService() {
        return env.getProperty(OpenApiConstants.INFO_TERMS);
    }

    @Override
    public String getInfoContactEmail() {
        return env.getProperty(OpenApiConstants.INFO_CONTACT_EMAIL);
    }

    @Override
    public String getInfoContactName() {
        return env.getProperty(OpenApiConstants.INFO_CONTACT_NAME);
    }

    @Override
    public String getInfoContactUrl() {
        return env.getProperty(OpenApiConstants.INFO_CONTACT_URL);
    }

    @Override
    public String getInfoLicenseName() {
        return env.getProperty(OpenApiConstants.INFO_LICENSE_NAME);
    }

    @Override
    public String getInfoLicenseUrl() {
        return env.getProperty(OpenApiConstants.INFO_LICENSE_URL);
    }

    @Override
    public OperationIdStrategy getOperationIdStrategy() {
        String strategy = env.getProperty(OpenApiConstants.OPERATION_ID_STRAGEGY);
        if (strategy != null) {
            return OperationIdStrategy.valueOf(strategy);
        }
        return null;
    }

}
