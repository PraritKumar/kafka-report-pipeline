package io.github.praritkumar.kafka_report_pipeline.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Service
public class ReportProcessor {

    private static final Logger log = LoggerFactory.getLogger(ReportProcessor.class);

    private final ReportRequestRepository repository;
    private final TemplateRenderer renderer;
    private final ReportFileStorage storage;

    public ReportProcessor(ReportRequestRepository repository,
                           TemplateRenderer renderer,
                           ReportFileStorage storage) {
        this.repository = repository;
        this.renderer = renderer;
        this.storage = storage;
    }

    public void process(UUID id) {
        int claimed = repository.transition(id, ReportStatus.INITIATED, ReportStatus.IN_PROGRESS, Instant.now());
        if (claimed == 0) {
            log.info("Skipping {}: not in INITIATED (already claimed, finished, or unknown)", id);
            return;
        }

        log.info("Processing report request {}", id);
        try {
            ReportRequest request = repository.findById(id)
                    .orElseThrow(() -> new ReportNotFoundException(id));

            String content = renderer.render(request.getReportType(), Map.of(
                    "id", id.toString(),
                    "reportType", request.getReportType(),
                    "generatedAt", Instant.now().toString(),
                    "parameters", request.getParameters() == null ? "-" : request.getParameters()));

            String fileName = storage.write(id, content);
            repository.finish(id, ReportStatus.IN_PROGRESS, ReportStatus.COMPLETED, fileName, null, Instant.now());
            log.info("Completed report request {} -> {}", id, fileName);
        } catch (RuntimeException ex) {
            log.error("Report request {} failed", id, ex);
            repository.finish(id, ReportStatus.IN_PROGRESS, ReportStatus.FAILED, null,
                    truncate(ex.getMessage()), Instant.now());
        }
    }

    private static String truncate(String message) {
        if (message == null) {
            return "Report generation failed";
        }
        return message.length() <= 500 ? message : message.substring(0, 500);
    }
}