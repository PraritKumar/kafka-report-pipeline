package io.github.praritkumar.kafka_report_pipeline.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ReportRequestPublisher {

    private static final Logger log = LoggerFactory.getLogger(ReportRequestPublisher.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public ReportRequestPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    void onReportRequested(ReportRequestedEvent event) {
        String id = event.id().toString();
        kafkaTemplate.send(KafkaTopicConfig.REPORT_REQUESTS_TOPIC, id, id)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to publish report request {}", id, ex);
                    } else {
                        log.info("Published report request {} to partition {}", id,
                                result.getRecordMetadata().partition());
                    }
                });
    }
}