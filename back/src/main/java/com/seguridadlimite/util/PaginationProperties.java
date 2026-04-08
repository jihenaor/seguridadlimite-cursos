package com.seguridadlimite.util;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "pagination")
public class PaginationProperties {
    private int defaultPage = 0;
    private int defaultPageSize = 10;

    public void setDefaultPage(int defaultPage) {
        this.defaultPage = defaultPage;
    }

    public void setDefaultPageSize(int defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }
}
