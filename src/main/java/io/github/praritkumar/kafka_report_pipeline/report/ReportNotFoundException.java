package io.github.praritkumar.kafka_report_pipeline.report;

import java.util.UUID;

public class ReportNotFoundException extends RuntimeException {
    public ReportNotFoundException(UUID id) {
        super("Report request not found: " + id);
    }
}