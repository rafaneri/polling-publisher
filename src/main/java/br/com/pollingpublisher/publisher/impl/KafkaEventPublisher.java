package br.com.pollingpublisher.publisher.impl;

import br.com.pollingpublisher.domain.PollingEvent;
import br.com.pollingpublisher.publisher.EventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventPublisher implements EventPublisher<PollingEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaEventPublisher.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${publisher.kafka.topic}")
    private String topic;

    public KafkaEventPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(PollingEvent event) {
        LOGGER.info("Publicando evento no Kafka: {}", event.getId());
        kafkaTemplate.send(topic, event.toString());
    }
}
