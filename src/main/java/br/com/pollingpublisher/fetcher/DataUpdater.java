package br.com.pollingpublisher.fetcher;

import br.com.pollingpublisher.domain.PollingEvent;

public interface DataUpdater<T extends PollingEvent> {
    void updateStatus(T event);
}
