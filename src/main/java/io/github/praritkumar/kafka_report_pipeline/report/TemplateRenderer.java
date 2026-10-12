package io.github.praritkumar.kafka_report_pipeline.report;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TemplateRenderer {

    private static final Pattern REPORT_TYPE = Pattern.compile("[A-Z0-9_]+");
    private static final Pattern PLACEHOLDER = Pattern.compile("\\{\\{(\\w+)}}");

    public String render(String reportType, Map<String, String> values) {
        if (!REPORT_TYPE.matcher(reportType).matches()) {
            throw new UnknownReportTypeException(reportType);
        }
        ClassPathResource resource = new ClassPathResource("templates/reports/" + reportType + ".txt");
        if (!resource.exists()) {
            throw new UnknownReportTypeException(reportType);
        }
        String template = read(resource);
        Matcher matcher = PLACEHOLDER.matcher(template);
        StringBuilder out = new StringBuilder();
        while (matcher.find()) {
            String value = values.getOrDefault(matcher.group(1), "");
            matcher.appendReplacement(out, Matcher.quoteReplacement(value));
        }
        matcher.appendTail(out);
        return out.toString();
    }

    private String read(ClassPathResource resource) {
        try (InputStream in = resource.getInputStream()) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}