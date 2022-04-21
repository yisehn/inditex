package es.inditex.ecommerce.poc.ports;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.inditex.ecommerce.poc.model.Price;

@Service
public interface PriceService {
  public Optional<Price> getPriceInfoByDateProductAndBrand(LocalDateTime applicationDate, String productId,
      String brandId);
}
