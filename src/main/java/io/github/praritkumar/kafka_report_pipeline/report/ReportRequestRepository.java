package io.github.praritkumar.kafka_report_pipeline.report;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ReportRequestRepository extends JpaRepository<ReportRequest, UUID> {
}