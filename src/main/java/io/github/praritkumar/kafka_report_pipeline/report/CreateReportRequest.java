package io.github.praritkumar.kafka_report_pipeline.report;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateReportRequest(
        @NotBlank @Size(max = 50) String reportType,
        String parameters) {
}