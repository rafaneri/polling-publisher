package br.com.pollingpublisher.publisher;

import br.com.pollingpublisher.domain.PollingEvent;

public interface EventPublisher<T extends PollingEvent> {
    void publish(T event);
}