package io.github.praritkumar.kafka_report_pipeline.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.UUID;

public interface ReportRequestRepository extends JpaRepository<ReportRequest, UUID> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update ReportRequest r set r.status = :to, r.updatedAt = :now "
            + "where r.id = :id and r.status = :from")
    int transition(@Param("id") UUID id,
                   @Param("from") ReportStatus from,
                   @Param("to") ReportStatus to,
                   @Param("now") Instant now);
}