package io.github.praritkumar.kafka_report_pipeline.report;

import java.util.UUID;

public record ReportRequestedEvent(UUID id) {
}