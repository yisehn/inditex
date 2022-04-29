package es.inditex.ecommerce.poc.port.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import es.inditex.ecommerce.poc.domain.model.Price;

@Repository
public interface PriceRepository {

  List<Price> findApplicatedPriceBy(String productId, String brandId, LocalDateTime appDate);
}
