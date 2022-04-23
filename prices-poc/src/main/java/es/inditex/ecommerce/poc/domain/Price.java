package es.inditex.ecommerce.poc.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Price {

  String productId;
  String brandId;
  String priceList;
  float finalPrice;
  LocalDateTime initialDate;
  LocalDateTime endDate;
}
