package es.inditex.ecommerce.poc.handler;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiError {
  String timestamp;
  String error;
  String message;
  String path;
}
