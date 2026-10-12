package io.github.praritkumar.kafka_report_pipeline.report;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class ReportRequestListener {

    private final ReportProcessor processor;

    public ReportRequestListener(ReportProcessor processor) {
        this.processor = processor;
    }

    @KafkaListener(topics = KafkaTopicConfig.REPORT_REQUESTS_TOPIC)
    void onMessage(String message) {
        UUID id;
        try {
            id = UUID.fromString(message);
        } catch (IllegalArgumentException ex) {
            throw new InvalidReportMessageException("Message is not a valid report id: " + message, ex);
        }
        processor.process(id);
    }
}