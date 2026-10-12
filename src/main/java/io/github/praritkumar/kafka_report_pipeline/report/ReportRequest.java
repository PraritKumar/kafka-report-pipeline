package io.github.praritkumar.kafka_report_pipeline.report;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "report_request")
public class ReportRequest {

    @Id
    private UUID id;

    @Column(name = "report_type", nullable = false, length = 50)
    private String reportType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReportStatus status;

    private String parameters;

    @Column(name = "file_path", length = 500)
    private String filePath;

    @Column(name = "error_message", length = 500)
    private String errorMessage;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected ReportRequest() {
    }

    public ReportRequest(String reportType, String parameters) {
        this.id = UUID.randomUUID();
        this.reportType = reportType;
        this.parameters = parameters;
        this.status = ReportStatus.INITIATED;
    }

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public String getReportType() { return reportType; }
    public ReportStatus getStatus() { return status; }
    public String getParameters() { return parameters; }
    public String getFilePath() { return filePath; }
    public String getErrorMessage() { return errorMessage; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}