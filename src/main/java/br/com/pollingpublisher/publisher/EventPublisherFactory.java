package br.com.pollingpublisher.publisher;

import br.com.pollingpublisher.publisher.impl.KafkaEventPublisher;
import br.com.pollingpublisher.publisher.impl.SnsEventPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventPublisherFactory {

    @Value("${publisher.type}")
    private String publisherType;

    private final KafkaEventPublisher kafkaPublisher;
    private final SnsEventPublisher snsPublisher;

    public EventPublisherFactory(KafkaEventPublisher kafkaPublisher, SnsEventPublisher snsPublisher) {
        this.kafkaPublisher = kafkaPublisher;
        this.snsPublisher = snsPublisher;
    }

    @Bean
    public EventPublisher eventPublisher() {
        return switch (publisherType.toLowerCase()) {
            case "sns" -> snsPublisher;
            case "kafka" -> kafkaPublisher;
            default -> throw new IllegalArgumentException("Tipo de publisher não suportado: " + publisherType);
        };
    }
}
