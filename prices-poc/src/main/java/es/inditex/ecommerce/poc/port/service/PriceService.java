package es.inditex.ecommerce.poc.port.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.inditex.ecommerce.poc.domain.model.Price;

@Service
public interface PriceService {
  public Optional<Price> getApplicatedPriceByDateProductAndBrand(LocalDateTime applicationDate, String productId,
      String brandId);
}
