package io.github.praritkumar.kafka_report_pipeline.report;

public class InvalidReportMessageException extends RuntimeException {
    public InvalidReportMessageException(String message, Throwable cause) {
        super(message, cause);
    }
}