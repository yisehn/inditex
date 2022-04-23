package es.inditex.ecommerce.poc.handlers;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class PriceExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    log.error("Unable to run the requested operation.MethodArgumentNotValidException", e);

    return ResponseEntity.status(status)
        .body(ApiError.builder().timestamp(LocalDateTime.now().toString()).status(status.name())
            .error("MethodArgumentNotValidException").message(e.getMessage())
            .path(((ServletWebRequest) request).getRequest().getRequestURI()).build());
  }

  @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
  public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e,
      HttpServletRequest request) throws IOException {
    log.error("Unable to run the requested operation.MethodArgumentTypeMismatchException", e);

    return ResponseEntity.badRequest()
        .body(ApiError.builder().timestamp(LocalDateTime.now().toString()).status(HttpStatus.BAD_REQUEST.name())
            .error("MethodArgumentTypeMismatchException").message(e.getMessage()).path(request.getRequestURI())
            .build());
  }

  @ExceptionHandler({ HttpMessageConversionException.class })
  public ResponseEntity<Object> handle(HttpMessageConversionException e, HttpServletRequest request) {
    log.error("Unable to run the requested operation.HttpMessageConversionException", e);

    return ResponseEntity.badRequest()
        .body(ApiError.builder().timestamp(LocalDateTime.now().toString()).status(HttpStatus.BAD_REQUEST.name())
            .error("HttpMessageConversionException").message(e.getMessage()).path(request.getRequestURI()).build());
  }

  @Override
  public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    log.error("Unable to run the requested operation.HttpMessageNotReadableException", e);

    return ResponseEntity.status(status)
        .body(ApiError.builder().timestamp(LocalDateTime.now().toString()).status(status.name())
            .error("HttpMessageNotReadableException").message(e.getMessage())
            .path(((ServletWebRequest) request).getRequest().getRequestURI()).build());
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    log.error("Unable to run the requested operation.MissingServletRequestParameterException", e);

    return ResponseEntity.status(status)
        .body(ApiError.builder().timestamp(LocalDateTime.now().toString()).status(status.name())
            .error("MissingServletRequestParameterException").message(e.getMessage())
            .path(((ServletWebRequest) request).getRequest().getRequestURI()).build());
  }

  @ExceptionHandler({ RuntimeException.class })
  public ResponseEntity<Object> handle(RuntimeException e, HttpServletRequest request) {
    log.error("Unable to run the requested operation. RuntimeException", e);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
        ApiError.builder().timestamp(LocalDateTime.now().toString()).status(HttpStatus.INTERNAL_SERVER_ERROR.name())
            .error("RuntimeException").message(e.getMessage()).path(request.getRequestURI()).build());
  }

  @ExceptionHandler({ Exception.class })
  public ResponseEntity<Object> handle(Exception e, HttpServletRequest request) {
    log.error("Unable to run the requested operation. Exception", e);

    return ResponseEntity.badRequest()
        .body(ApiError.builder().timestamp(LocalDateTime.now().toString()).status(HttpStatus.BAD_REQUEST.name())
            .error("Exception").message(e.getMessage()).path(request.getRequestURI()).build());
  }
}
