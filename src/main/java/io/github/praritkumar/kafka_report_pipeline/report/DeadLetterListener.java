package io.github.praritkumar.kafka_report_pipeline.report;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;

@Component
public class DeadLetterListener {

    private static final Logger log = LoggerFactory.getLogger(DeadLetterListener.class);

    @KafkaListener(topics = KafkaTopicConfig.REPORT_REQUESTS_DLT, groupId = "report-dlt-logger")
    void onDeadLetter(ConsumerRecord<String, String> record) {
        Header header = record.headers().lastHeader(KafkaHeaders.DLT_EXCEPTION_MESSAGE);
        String reason = header == null ? "unknown" : new String(header.value(), StandardCharsets.UTF_8);
        log.warn("Dead-lettered message key={} value={} reason={}", record.key(), record.value(), reason);
    }
}