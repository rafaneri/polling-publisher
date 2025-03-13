package br.com.pollingpublisher.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "polling.publisher")
public class PollingPublisherProperties {
    private boolean enabled;
    private int batchSize = 10;
}
