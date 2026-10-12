package io.github.praritkumar.kafka_report_pipeline.report;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class ReportService {

    private final ReportRequestRepository repository;

    public ReportService(ReportRequestRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ReportResponse create(CreateReportRequest request) {
        ReportRequest saved = repository.save(
                new ReportRequest(request.reportType(), request.parameters()));
        return ReportResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public ReportResponse get(UUID id) {
        return repository.findById(id)
                .map(ReportResponse::from)
                .orElseThrow(() -> new ReportNotFoundException(id));
    }
}