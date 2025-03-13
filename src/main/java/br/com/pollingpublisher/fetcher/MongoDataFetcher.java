package br.com.pollingpublisher.fetcher;

import br.com.pollingpublisher.domain.PollingEvent;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MongoDataFetcher implements DataFetcher<PollingEvent> {

    private final MongoTemplate mongoTemplate;
    private final int batchSize;

    public MongoDataFetcher(MongoTemplate mongoTemplate, int batchSize) {
        this.mongoTemplate = mongoTemplate;
        this.batchSize = batchSize;
    }

    @Override
    public List<PollingEvent> fetchData() {
        List<PollingEvent> events = new ArrayList<>();

        for (int i = 0; i < batchSize; i++) {
            Query query = new Query(Criteria.where("status").is("PENDING")).limit(1);
            Update update = new Update().set("status", "SENDING").set("updatedAt", LocalDateTime.now());

            PollingEvent event = mongoTemplate.findAndModify(query, update, PollingEvent.class);
            if (event != null) events.add(event);
            else break;
        }

        return events;
    }
}