package es.inditex.ecommerce.poc.domain;

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
  Float finalPrice;
  private Integer priority;
  LocalDateTime startDate;
  LocalDateTime endDate;
}
