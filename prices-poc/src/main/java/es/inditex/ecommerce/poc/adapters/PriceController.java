package es.inditex.ecommerce.poc.adapters;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.inditex.ecommerce.poc.model.Price;
import es.inditex.ecommerce.poc.ports.PriceService;

@RestController
@RequestMapping("/v1/ecommerce/prices")
public class PriceController {

  @Autowired
  PriceService priceService;

  @GetMapping
  public ResponseEntity<Price> getPriceInfoByDateProductAndBrand(HttpServletRequest request,
      @RequestParam("application_date") LocalDateTime applicationDate, @RequestParam("product_id") String productId,
      @RequestParam("brand_id") String brandId) {

    Optional<Price> price = priceService.getPriceInfoByDateProductAndBrand(applicationDate, productId, brandId);
    if (price.isPresent()) {
      return ResponseEntity.ok(price.get());
    }
    return ResponseEntity.notFound().build();
  }
}
