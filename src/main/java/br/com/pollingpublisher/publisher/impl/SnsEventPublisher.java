package br.com.pollingpublisher.publisher.impl;

import br.com.pollingpublisher.domain.PollingEvent;
import br.com.pollingpublisher.publisher.EventPublisher;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SnsEventPublisher implements EventPublisher<PollingEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SnsEventPublisher.class);

    private final AmazonSNS snsClient;

    @Value("${publisher.sns.topicArn}")
    private String topicArn;

    public SnsEventPublisher(AmazonSNS snsClient) {
        this.snsClient = snsClient;
    }

    @Override
    public void publish(PollingEvent event) {
        LOGGER.info("Publicando evento no SNS: {}", event.getId());
        snsClient.publish(new PublishRequest(topicArn, event.toString()));
    }
}
