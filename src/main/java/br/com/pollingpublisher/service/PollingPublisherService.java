package br.com.pollingpublisher.service;

import br.com.pollingpublisher.domain.PollingEvent;
import br.com.pollingpublisher.fetcher.DataFetcher;
import br.com.pollingpublisher.fetcher.DataUpdater;
import br.com.pollingpublisher.publisher.EventPublisher;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public class PollingPublisherService<T extends PollingEvent> {

    private final DataFetcher<T> dataFetcher;
    private final DataUpdater<T> dataUpdater;
    private final EventPublisher<T> eventPublisher;

    public PollingPublisherService(DataFetcher<T> dataFetcher, DataUpdater<T> dataUpdater, EventPublisher<T> eventPublisher) {
        this.dataFetcher = dataFetcher;
        this.dataUpdater = dataUpdater;
        this.eventPublisher = eventPublisher;
    }

    @Scheduled(fixedDelayString = "${polling.publisher.interval:5000}")
    public void process() {
        List<T> events = dataFetcher.fetchData();
        events.forEach(event -> {
            eventPublisher.publish(event);
            dataUpdater.updateStatus(event);
        });

    }
}
