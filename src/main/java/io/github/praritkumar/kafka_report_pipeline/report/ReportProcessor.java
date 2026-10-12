package io.github.praritkumar.kafka_report_pipeline.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.UUID;

@Service
public class ReportProcessor {

    private static final Logger log = LoggerFactory.getLogger(ReportProcessor.class);

    private final ReportRequestRepository repository;

    public ReportProcessor(ReportRequestRepository repository) {
        this.repository = repository;
    }

    public void process(UUID id) {
        int claimed = repository.transition(id, ReportStatus.INITIATED, ReportStatus.IN_PROGRESS, Instant.now());
        if (claimed == 0) {
            log.info("Skipping {}: not in INITIATED (already claimed, finished, or unknown)", id);
            return;
        }

        log.info("Processing report request {}", id);
        // Next step: generate the file from a template here.

        repository.transition(id, ReportStatus.IN_PROGRESS, ReportStatus.COMPLETED, Instant.now());
        log.info("Completed report request {}", id);
    }
}