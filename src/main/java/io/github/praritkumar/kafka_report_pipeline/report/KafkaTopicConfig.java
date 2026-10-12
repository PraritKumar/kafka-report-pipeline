package io.github.praritkumar.kafka_report_pipeline.report;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public static final String REPORT_REQUESTS_TOPIC = "report-requests";
    public static final String REPORT_REQUESTS_DLT = "report-requests.DLT";

    @Bean
    NewTopic reportRequestsTopic() {
        return TopicBuilder.name(REPORT_REQUESTS_TOPIC).partitions(3).replicas(1).build();
    }

    @Bean
    NewTopic reportRequestsDlt() {
        return TopicBuilder.name(REPORT_REQUESTS_DLT).partitions(3).replicas(1).build();
    }
}