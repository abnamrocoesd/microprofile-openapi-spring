package com.abnamro.coesd.springboot.openapi;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mp.openapi")
public class OpenAPIConfig {

    private Scan scan;
    private String filter;

    public Scan getScan() {
        return scan;
    }

    public void setScan(Scan scan) {
        this.scan = scan;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public static class Scan {

        private Boolean disable;

        public Boolean getDisable() {
            return disable;
        }

        public void setDisable(Boolean disable) {
            this.disable = disable;
        }
    }

}
