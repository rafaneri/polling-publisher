package br.com.pollingpublisher.fetcher;

import br.com.pollingpublisher.domain.PollingEvent;

import java.util.List;

public interface DataFetcher<T extends PollingEvent> {
    List<T> fetchData();
}
