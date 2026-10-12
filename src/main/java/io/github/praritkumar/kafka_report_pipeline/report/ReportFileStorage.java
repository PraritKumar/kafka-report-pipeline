package io.github.praritkumar.kafka_report_pipeline.report;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Component
public class ReportFileStorage {

    private final Path outputDir;

    public ReportFileStorage(@Value("${report.output-dir:./generated}") String outputDir) {
        this.outputDir = Path.of(outputDir).toAbsolutePath().normalize();
    }

    /** Writes the report and returns the file name (not the full path). */
    public String write(UUID id, String content) {
        String fileName = id + ".txt";
        try {
            Files.createDirectories(outputDir);
            Files.writeString(outputDir.resolve(fileName), content, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return fileName;
    }

    public Path resolve(String fileName) {
        Path file = outputDir.resolve(fileName).normalize();
        if (!file.startsWith(outputDir)) {
            throw new IllegalArgumentException("Invalid file name");
        }
        return file;
    }
}