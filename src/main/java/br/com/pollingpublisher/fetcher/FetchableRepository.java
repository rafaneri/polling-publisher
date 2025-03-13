package br.com.pollingpublisher.fetcher;

import java.util.List;

public interface FetchableRepository<T> {
    List<T> findFetchableData();
    void updateStatusToSent(List<T> events);
}
