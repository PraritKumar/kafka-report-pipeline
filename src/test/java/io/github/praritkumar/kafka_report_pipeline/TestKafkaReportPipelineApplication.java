package io.github.praritkumar.kafka_report_pipeline;

import org.springframework.boot.SpringApplication;

public class TestKafkaReportPipelineApplication {

	public static void main(String[] args) {
		SpringApplication.from(KafkaReportPipelineApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
