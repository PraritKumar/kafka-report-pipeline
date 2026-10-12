package io.github.praritkumar.kafka_report_pipeline.report;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class ReportService {

    private final ReportRequestRepository repository;
    private final ApplicationEventPublisher events;

    public ReportService(ReportRequestRepository repository, ApplicationEventPublisher events) {
        this.repository = repository;
        this.events = events;
    }

    @Transactional
    public ReportResponse create(CreateReportRequest request) {
        ReportRequest saved = repository.save(
                new ReportRequest(request.reportType(), request.parameters()));
        events.publishEvent(new ReportRequestedEvent(saved.getId()));
        return ReportResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public ReportResponse get(UUID id) {
        return repository.findById(id)
                .map(ReportResponse::from)
                .orElseThrow(() -> new ReportNotFoundException(id));
    }
}