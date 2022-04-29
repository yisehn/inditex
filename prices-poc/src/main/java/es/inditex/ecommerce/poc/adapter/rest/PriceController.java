package es.inditex.ecommerce.poc.adapter.rest;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.inditex.ecommerce.poc.domain.model.Price;
import es.inditex.ecommerce.poc.port.service.PriceService;

@RestController
@RequestMapping("/v1/ecommerce/prices")
public class PriceController {

  @Autowired
  PriceService priceService;

  @GetMapping
  public ResponseEntity<Price> getPriceInfoByDateProductAndBrand(
      @RequestParam("application_date") LocalDateTime applicationDate, @RequestParam("product_id") String productId,
      @RequestParam("brand_id") String brandId) {

    Optional<Price> price = priceService.getApplicatedPriceByDateProductAndBrand(applicationDate, productId, brandId);
    return price.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }
}
