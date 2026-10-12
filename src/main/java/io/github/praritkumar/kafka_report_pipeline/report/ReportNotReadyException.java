package io.github.praritkumar.kafka_report_pipeline.report;

import java.util.UUID;

public class ReportNotReadyException extends RuntimeException {
    public ReportNotReadyException(UUID id, ReportStatus status) {
        super("Report " + id + " is not ready (status: " + status + ")");
    }
}