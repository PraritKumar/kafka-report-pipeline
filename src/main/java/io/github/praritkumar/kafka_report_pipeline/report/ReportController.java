package io.github.praritkumar.kafka_report_pipeline.report;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ReportResponse> create(@Valid @RequestBody CreateReportRequest request) {
        ReportResponse created = service.create(request);
        return ResponseEntity.accepted()
                .location(URI.create("/reports/" + created.id()))
                .body(created);
    }

    @GetMapping("/{id}")
    public ReportResponse get(@PathVariable UUID id) {
        return service.get(id);
    }
}