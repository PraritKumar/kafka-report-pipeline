package io.github.praritkumar.kafka_report_pipeline.report;

import jakarta.validation.Valid;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.nio.file.Path;
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

    @GetMapping("/{id}/file")
    public ResponseEntity<Resource> download(@PathVariable UUID id) {
        Path file = service.getFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment().filename("report-" + id + ".txt").build().toString())
                .body(new FileSystemResource(file));
    }
}