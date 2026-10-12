package io.github.praritkumar.kafka_report_pipeline.report;

import java.time.Instant;
import java.util.UUID;

public record ReportResponse(
        UUID id,
        String reportType,
        ReportStatus status,
        String filePath,
        String errorMessage,
        Instant createdAt,
        Instant updatedAt) {

    static ReportResponse from(ReportRequest r) {
        return new ReportResponse(r.getId(), r.getReportType(), r.getStatus(),
                r.getFilePath(), r.getErrorMessage(), r.getCreatedAt(), r.getUpdatedAt());
    }
}