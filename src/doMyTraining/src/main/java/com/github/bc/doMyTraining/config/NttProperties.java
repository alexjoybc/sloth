package com.github.bc.doMyTraining.config;

import com.github.bc.doMyTraining.catalys.Catalys;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ntt")
public class NttProperties {

    private Catalys catalys;

    public Catalys getCatalys() {
        return catalys;
    }

    public void setCatalys(Catalys catalys) {
        this.catalys = catalys;
    }
}
