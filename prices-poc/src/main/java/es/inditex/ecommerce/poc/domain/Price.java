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

  private String productId;
  private String brandId;
  private String priceList;
  private Float finalPrice;
  private Integer priority;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
}
