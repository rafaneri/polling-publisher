package br.com.pollingpublisher.fetcher;

import br.com.pollingpublisher.domain.PollingEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostgresDataFetcher implements DataFetcher<PollingEvent> {

    private final JdbcTemplate jdbcTemplate;
    private final int batchSize;

    public PostgresDataFetcher(JdbcTemplate jdbcTemplate, int batchSize) {
        this.jdbcTemplate = jdbcTemplate;
        this.batchSize = batchSize;
    }

    @Override
    public List<PollingEvent> fetchData() {
        return jdbcTemplate.query(
                "UPDATE event SET status = 'SENDING' WHERE id IN (" +
                        "SELECT id FROM event WHERE status = 'PENDING' LIMIT ?) RETURNING *",
                new Object[]{batchSize}, (rs, rowNum) -> PollingEvent.builder().id(rs.getString("id")).status(rs.getString("status")).build()
        );
    }
}
