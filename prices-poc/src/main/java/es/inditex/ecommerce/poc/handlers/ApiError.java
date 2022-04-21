package es.inditex.ecommerce.poc.handlers;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiError {

  String timestamp;
  String status;
  String error;
  String message;
  String path;

}
