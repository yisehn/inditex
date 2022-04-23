package es.inditex.ecommerce.poc.port;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import es.inditex.ecommerce.poc.domain.Price;

@Repository
public interface PriceRepository {

  List<Price> findApplicatedPriceBy(String productId, String brandId, LocalDateTime startDate, LocalDateTime endDate);
}
