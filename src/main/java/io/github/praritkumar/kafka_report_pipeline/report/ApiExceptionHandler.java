package io.github.praritkumar.kafka_report_pipeline.report;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ReportNotFoundException.class)
    ProblemDetail notFound(ReportNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Report not found");
        return problem;
    }

    @ExceptionHandler(ReportNotReadyException.class)
    ProblemDetail notReady(ReportNotReadyException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problem.setTitle("Report not ready");
        return problem;
    }
}