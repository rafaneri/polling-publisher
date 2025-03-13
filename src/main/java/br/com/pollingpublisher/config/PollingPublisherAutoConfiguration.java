package br.com.pollingpublisher.config;

import br.com.pollingpublisher.fetcher.DataFetcher;
import br.com.pollingpublisher.fetcher.DataUpdater;
import br.com.pollingpublisher.publisher.EventPublisher;
import br.com.pollingpublisher.service.PollingPublisherService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PollingPublisherAutoConfiguration {

    private final EventPublisher<?> eventPublisher;
    private final DataFetcher<?> dataFetcher;
    private final DataUpdater<?> dataUpdater;

    public PollingPublisherAutoConfiguration(EventPublisher<?> eventPublisher, DataFetcher<?> dataFetcher, DataUpdater<?> dataUpdater) {
        this.eventPublisher = eventPublisher;
        this.dataFetcher = dataFetcher;
        this.dataUpdater = dataUpdater;
    }

    @Bean
    @ConditionalOnMissingBean
    public PollingPublisherService<?> pollingPublisherService() {
        return new PollingPublisherService(dataFetcher, dataUpdater, eventPublisher);
    }
}
