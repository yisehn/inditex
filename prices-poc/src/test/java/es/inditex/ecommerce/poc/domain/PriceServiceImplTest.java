package es.inditex.ecommerce.poc.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
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

import es.inditex.ecommerce.poc.entities.PersistencePrice;
import es.inditex.ecommerce.poc.model.Price;
import es.inditex.ecommerce.poc.ports.PriceRepository;

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
      when(priceRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
          Mockito.anyString(), Mockito.anyString(), Mockito.any(Timestamp.class), Mockito.any(Timestamp.class)))
              .thenReturn(Collections.emptyList());

      Optional<Price> price = priceServiceImpl.getPriceInfoByDateProductAndBrand(LocalDateTime.now(), "productId",
          "brandId");
      assertTrue(!price.isPresent());

    } catch (Exception exc) {
      fail();
    }
  }

  @Test
  void whenMoreThanOneRowMatchThenReturnTheHigherPriorityElement() {
    List<PersistencePrice> persistencePrices = new ArrayList<PersistencePrice>();
    Instant now = Instant.now();
    Instant now1 = Instant.now();

    persistencePrices.add(PersistencePrice.builder().id(Long.valueOf("1")).brandId("brandId")
        .startDate(Timestamp.from(now)).endDate(Timestamp.from(now)).priceList("priceList").productId("productId")
        .priority(1).price(Float.valueOf("1")).curr("EUR").build());

    persistencePrices.add(PersistencePrice.builder().id(Long.valueOf("2")).brandId("brandId")
        .startDate(Timestamp.from(now1)).endDate(Timestamp.from(now1)).priceList("priceList1").productId("productId")
        .priority(2).price(Float.valueOf("2")).curr("EUR").build());

    persistencePrices.add(PersistencePrice.builder().id(Long.valueOf("3")).brandId("brandId")
        .startDate(Timestamp.from(now1)).endDate(Timestamp.from(now1)).priceList("priceList3").productId("productId")
        .priority(1).price(Float.valueOf("3")).curr("EUR").build());

    when(priceRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
        Mockito.anyString(), Mockito.anyString(), Mockito.any(Timestamp.class), Mockito.any(Timestamp.class)))
            .thenReturn(persistencePrices);

    Optional<Price> price = priceServiceImpl.getPriceInfoByDateProductAndBrand(LocalDateTime.now(), "productId",
        "brandId");
    assertTrue(price.isPresent());

    assertEquals("brandId", price.get().getBrandId());
    assertEquals(Timestamp.from(now1).toLocalDateTime(), price.get().getEndDate());
    assertEquals(Timestamp.from(now1).toLocalDateTime(), price.get().getInitialDate());
    assertEquals(Float.valueOf("2"), price.get().getFinalPrice());
    assertEquals("priceList1", price.get().getPriceList());
    assertEquals("productId", price.get().getProductId());

    try {
    } catch (Exception exc) {
      fail();
    }
  }

  @Test
  void whenMoreThanOneRowMatchThenReturnTheFirsRecoveredtHigherPriorityElement() {
    List<PersistencePrice> persistencePrices = new ArrayList<PersistencePrice>();
    Instant now = Instant.now();
    Instant now1 = Instant.now();

    persistencePrices.add(PersistencePrice.builder().id(Long.valueOf("1")).brandId("brandId")
        .startDate(Timestamp.from(now)).endDate(Timestamp.from(now)).priceList("priceList").productId("productId")
        .priority(1).price(Float.valueOf("1")).curr("EUR").build());

    persistencePrices.add(PersistencePrice.builder().id(Long.valueOf("2")).brandId("brandId")
        .startDate(Timestamp.from(now1)).endDate(Timestamp.from(now1)).priceList("priceList1").productId("productId")
        .priority(2).price(Float.valueOf("2")).curr("EUR").build());

    persistencePrices.add(PersistencePrice.builder().id(Long.valueOf("3")).brandId("brandId")
        .startDate(Timestamp.from(now1)).endDate(Timestamp.from(now1)).priceList("priceList3").productId("productId")
        .priority(2).price(Float.valueOf("3")).curr("EUR").build());

    when(priceRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
        Mockito.anyString(), Mockito.anyString(), Mockito.any(Timestamp.class), Mockito.any(Timestamp.class)))
            .thenReturn(persistencePrices);

    Optional<Price> price = priceServiceImpl.getPriceInfoByDateProductAndBrand(LocalDateTime.now(), "productId",
        "brandId");
    assertTrue(price.isPresent());

    assertEquals("brandId", price.get().getBrandId());
    assertEquals(Timestamp.from(now1).toLocalDateTime(), price.get().getEndDate());
    assertEquals(Timestamp.from(now1).toLocalDateTime(), price.get().getInitialDate());
    assertEquals(Float.valueOf("2"), price.get().getFinalPrice());
    assertEquals("priceList1", price.get().getPriceList());
    assertEquals("productId", price.get().getProductId());

    try {
    } catch (Exception exc) {
      fail();
    }
  }

  @Test
  void whenMoreThanOneRowMatchWithSamePriorityThenReturnTheFistRecoveredOne() {
    try {

      List<PersistencePrice> persistencePrices = new ArrayList<PersistencePrice>();
      Instant now = Instant.now();

      persistencePrices.add(PersistencePrice.builder().id(Long.valueOf("1")).brandId("brandId")
          .startDate(Timestamp.from(now)).endDate(Timestamp.from(now)).priceList("priceList").productId("productId")
          .priority(1).price(Float.valueOf("1")).curr("EUR").build());

      persistencePrices.add(PersistencePrice.builder().id(Long.valueOf("2")).brandId("brandId")
          .startDate(Timestamp.from(now)).endDate(Timestamp.from(now)).priceList("priceList1").productId("productId")
          .priority(1).price(Float.valueOf("2")).curr("EUR").build());

      when(priceRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
          Mockito.anyString(), Mockito.anyString(), Mockito.any(Timestamp.class), Mockito.any(Timestamp.class)))
              .thenReturn(persistencePrices);

      Optional<Price> price = priceServiceImpl.getPriceInfoByDateProductAndBrand(LocalDateTime.now(), "productId",
          "brandId");
      assertTrue(price.isPresent());

      assertEquals("brandId", price.get().getBrandId());
      assertEquals(Timestamp.from(now).toLocalDateTime(), price.get().getEndDate());
      assertEquals(Timestamp.from(now).toLocalDateTime(), price.get().getInitialDate());
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

      List<PersistencePrice> persistencePrices = new ArrayList<PersistencePrice>();
      Instant now = Instant.now();

      persistencePrices.add(PersistencePrice.builder().id(Long.valueOf("1")).brandId("brandId")
          .startDate(Timestamp.from(now)).endDate(Timestamp.from(now)).priceList("priceList").productId("productId")
          .priority(1).price(Float.valueOf("1")).curr("EUR").build());

      when(priceRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
          Mockito.anyString(), Mockito.anyString(), Mockito.any(Timestamp.class), Mockito.any(Timestamp.class)))
              .thenReturn(persistencePrices);

      Optional<Price> price = priceServiceImpl.getPriceInfoByDateProductAndBrand(LocalDateTime.now(), "productId",
          "brandId");
      assertTrue(price.isPresent());

      assertEquals("brandId", price.get().getBrandId());
      assertEquals(Timestamp.from(now).toLocalDateTime(), price.get().getEndDate());
      assertEquals(Timestamp.from(now).toLocalDateTime(), price.get().getInitialDate());
      assertEquals(Float.valueOf("1"), price.get().getFinalPrice());
      assertEquals("priceList", price.get().getPriceList());
      assertEquals("productId", price.get().getProductId());

    } catch (Exception exc) {
      fail();
    }
  }
}
