package es.inditex.ecommerce.poc.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import es.inditex.ecommerce.poc.mapper.ApiErrorMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class PriceExceptionHandler {

  final ApiErrorMapper errorMapper;

  private ApiError buildGeneralResponse(Exception e, HttpServletRequest request) {
    // To build here the decided ApiError structure
    String exceptionName = e.getClass().getSimpleName();
    String completePath = request.getMethod().concat(":").concat(request.getRequestURI());
    log.error("Unable to run the requested operation" + completePath, e);

    return errorMapper.toApiError(exceptionName, e, completePath);
  }

  @ExceptionHandler({ MethodArgumentTypeMismatchException.class, HttpMessageConversionException.class,
      Exception.class })
  public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(Exception e, HttpServletRequest request) {
    return ResponseEntity.badRequest().body(buildGeneralResponse(e, request));
  }

  @ExceptionHandler({ RuntimeException.class })
  public ResponseEntity<Object> handle(RuntimeException e, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildGeneralResponse(e, request));
  }
}