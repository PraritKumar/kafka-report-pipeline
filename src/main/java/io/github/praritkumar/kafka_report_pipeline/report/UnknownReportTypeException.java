package io.github.praritkumar.kafka_report_pipeline.report;

public class UnknownReportTypeException extends RuntimeException {
    public UnknownReportTypeException(String reportType) {
        super("Unknown report type: " + reportType);
    }
}