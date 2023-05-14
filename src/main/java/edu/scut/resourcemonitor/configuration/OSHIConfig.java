package edu.scut.resourcemonitor.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import oshi.SystemInfo;

@Configuration
public class OSHIConfig {
    @Bean
    SystemInfo getSystemInfo() {
        return new SystemInfo();
    }
}
