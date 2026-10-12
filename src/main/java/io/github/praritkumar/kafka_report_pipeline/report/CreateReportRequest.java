package io.github.praritkumar.kafka_report_pipeline.report;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateReportRequest(
        @NotBlank
        @Size(max = 50)
        @Pattern(regexp = "[A-Z0-9_]+", message = "must contain only A-Z, 0-9 and underscore")
        String reportType,
        String parameters) {
}