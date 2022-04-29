package es.inditex.ecommerce.poc.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import es.inditex.ecommerce.poc.domain.model.Price;
import es.inditex.ecommerce.poc.domain.service.PriceServiceImpl;
import es.inditex.ecommerce.poc.port.repository.PriceRepository;

@SpringBootTest
class PriceServiceImplTest {

  @MockBean
  PriceRepository priceRepository;

  PriceServiceImpl priceServiceImpl;

  @BeforeEach
  void init() throws Exception {
    priceServiceImpl = new PriceServiceImpl(priceRepository);
  }

  @Test
  void whenAnyRowMatchThenReturnAnEmptyValue() {
    try {
      when(priceRepository.findApplicatedPriceBy(Mockito.anyString(), Mockito.anyString(),
          Mockito.any(LocalDateTime.class))).thenReturn(Collections.emptyList());

      Optional<Price> price = priceServiceImpl.getApplicatedPriceByDateProductAndBrand(LocalDateTime.now(), "productId",
          "brandId");
      assertTrue(!price.isPresent());

    } catch (Exception exc) {
      fail();
    }
  }

  @Test
  void whenMoreThanOneRowMatchThenReturnTheHigherPriorityElement() {
    List<Price> prices = new ArrayList<Price>();
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime now1 = LocalDateTime.now();

    prices.add(Price.builder().brandId("brandId").startDate(now).endDate(now).priceList("priceList")
        .productId("productId").priority(1).finalPrice(Float.valueOf("1")).build());

    prices.add(Price.builder().brandId("brandId").startDate(now1).endDate(now1).priceList("priceList1")
        .productId("productId").priority(2).finalPrice(Float.valueOf("2")).build());

    prices.add(Price.builder().brandId("brandId").startDate(LocalDateTime.now()).endDate(LocalDateTime.now())
        .priceList("priceList3").productId("productId").priority(1).finalPrice(Float.valueOf("3")).build());

    when(priceRepository.findApplicatedPriceBy(Mockito.anyString(), Mockito.anyString(),
        Mockito.any(LocalDateTime.class))).thenReturn(prices);

    Optional<Price> price = priceServiceImpl.getApplicatedPriceByDateProductAndBrand(LocalDateTime.now(), "productId",
        "brandId");
    assertTrue(price.isPresent());

    assertEquals("brandId", price.get().getBrandId());
    assertEquals(now1, price.get().getEndDate());
    assertEquals(now1, price.get().getStartDate());
    assertEquals(Float.valueOf("2"), price.get().getFinalPrice());
    assertEquals("priceList1", price.get().getPriceList());
    assertEquals("productId", price.get().getProductId());
    assertEquals(2, price.get().getPriority());

    try {
    } catch (Exception exc) {
      fail();
    }
  }

  @Test
  void whenMoreThanOneRowMatchThenReturnTheFirsRecoveredtHigherPriorityElement() {
    List<Price> prices = new ArrayList<Price>();
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime now1 = LocalDateTime.now();

    prices.add(Price.builder().brandId("brandId").startDate(now).endDate(now).priceList("priceList")
        .productId("productId").priority(1).finalPrice(Float.valueOf("1")).build());

    prices.add(Price.builder().brandId("brandId").startDate(now1).endDate(now1).priceList("priceList1")
        .productId("productId").priority(2).finalPrice(Float.valueOf("2")).build());

    prices.add(Price.builder().brandId("brandId").startDate(now1).endDate(now1).priceList("priceList3")
        .productId("productId").priority(2).finalPrice(Float.valueOf("3")).build());

    when(priceRepository.findApplicatedPriceBy(Mockito.anyString(), Mockito.anyString(),
        Mockito.any(LocalDateTime.class))).thenReturn(prices);

    Optional<Price> price = priceServiceImpl.getApplicatedPriceByDateProductAndBrand(LocalDateTime.now(), "productId",
        "brandId");
    assertTrue(price.isPresent());

    assertEquals("brandId", price.get().getBrandId());
    assertEquals(now1, price.get().getEndDate());
    assertEquals(now1, price.get().getStartDate());
    assertEquals(Float.valueOf("2"), price.get().getFinalPrice());
    assertEquals("priceList1", price.get().getPriceList());
    assertEquals("productId", price.get().getProductId());
    assertEquals(2, price.get().getPriority());

    try {
    } catch (Exception exc) {
      fail();
    }
  }

  @Test
  void whenMoreThanOneRowMatchWithSamePriorityThenReturnTheFistRecoveredOne() {
    try {

      List<Price> prices = new ArrayList<Price>();
      LocalDateTime now = LocalDateTime.now();

      prices.add(Price.builder().brandId("brandId").startDate(now).endDate(now).priceList("priceList")
          .productId("productId").priority(1).finalPrice(Float.valueOf("1")).build());

      prices.add(Price.builder().brandId("brandId").startDate(now).endDate(now).priceList("priceList1")
          .productId("productId").priority(1).finalPrice(Float.valueOf("2")).build());

      when(priceRepository.findApplicatedPriceBy(Mockito.anyString(), Mockito.anyString(),
          Mockito.any(LocalDateTime.class))).thenReturn(prices);

      Optional<Price> price = priceServiceImpl.getApplicatedPriceByDateProductAndBrand(LocalDateTime.now(), "productId",
          "brandId");
      assertTrue(price.isPresent());

      assertEquals("brandId", price.get().getBrandId());
      assertEquals(now, price.get().getEndDate());
      assertEquals(now, price.get().getStartDate());
      assertEquals(Float.valueOf("1"), price.get().getFinalPrice());
      assertEquals("priceList", price.get().getPriceList());
      assertEquals("productId", price.get().getProductId());

    } catch (Exception exc) {
      fail();
    }
  }

  @Test
  void whenOnlyOneRowMatchThenReturnTheMatchedElement() {
    try {

      List<Price> prices = new ArrayList<Price>();
      LocalDateTime now = LocalDateTime.now();

      prices.add(Price.builder().brandId("brandId").startDate(now).endDate(now).priceList("priceList")
          .productId("productId").priority(1).finalPrice(Float.valueOf("1")).build());

      when(priceRepository.findApplicatedPriceBy(Mockito.anyString(), Mockito.anyString(),
          Mockito.any(LocalDateTime.class))).thenReturn(prices);

      Optional<Price> price = priceServiceImpl.getApplicatedPriceByDateProductAndBrand(LocalDateTime.now(), "productId",
          "brandId");
      assertTrue(price.isPresent());

      assertEquals("brandId", price.get().getBrandId());
      assertEquals(now, price.get().getEndDate());
      assertEquals(now, price.get().getStartDate());
      assertEquals(Float.valueOf("1"), price.get().getFinalPrice());
      assertEquals("priceList", price.get().getPriceList());
      assertEquals("productId", price.get().getProductId());
      assertEquals(1, price.get().getPriority());

    } catch (Exception exc) {
      fail();
    }
  }
}
