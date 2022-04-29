package es.inditex.ecommerce.poc.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import es.inditex.ecommerce.poc.adapter.PriceJpaRepository;
import es.inditex.ecommerce.poc.adapter.PriceRespositoryImpl;
import es.inditex.ecommerce.poc.domain.Price;
import es.inditex.ecommerce.poc.entity.PriceJpa;
import es.inditex.ecommerce.poc.mapper.PriceMapper;

@SpringBootTest
class PriceRespositoryImplTest {

  @MockBean
  PriceJpaRepository priceJpaRepository;

  @MockBean
  PriceMapper priceMapper;

  PriceRespositoryImpl priceRespositoryImpl;

  @BeforeEach
  void init() throws Exception {
    priceRespositoryImpl = new PriceRespositoryImpl(priceMapper, priceJpaRepository);
  }

  @Test
  void whenPersistencePriceIsFoundThenReturnMappedPrice() {

    // Given
    Instant yesterday = Instant.now().minus(1, ChronoUnit.DAYS);
    Instant tomorrow = Instant.now().plus(1, ChronoUnit.DAYS);
    Instant yesterday1 = Instant.now().minus(1, ChronoUnit.DAYS);
    Instant tomorrow1 = Instant.now().plus(1, ChronoUnit.DAYS);

    PriceJpa priceJpa1 = PriceJpa.builder().id(Long.valueOf("1")).brandId("brandId")
        .startDate(Timestamp.from(yesterday)).endDate(Timestamp.from(tomorrow)).priceList("priceList")
        .productId("productId").priority(Integer.valueOf("1")).price(Float.valueOf("2")).curr("EUR").build();

    PriceJpa priceJpa2 = PriceJpa.builder().id(Long.valueOf("2")).brandId("brandId")
        .startDate(Timestamp.from(yesterday1)).endDate(Timestamp.from(tomorrow1)).priceList("priceList2")
        .productId("productId").priority(Integer.valueOf("2")).price(Float.valueOf("3")).curr("EUR").build();

    List<PriceJpa> prices = Arrays.asList(priceJpa1, priceJpa2);

    // When
    when(priceJpaRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
        Mockito.anyString(), Mockito.anyString(), Mockito.any(Timestamp.class), Mockito.any(Timestamp.class)))
            .thenReturn(prices);

    when(priceMapper.toDomain(priceJpa1)).thenReturn(Price.builder().productId("productId").brandId("brandId")
        .startDate(Timestamp.from(yesterday).toLocalDateTime()).endDate(Timestamp.from(tomorrow).toLocalDateTime())
        .priceList("priceList").priority(Integer.valueOf("1")).finalPrice(Float.valueOf("2")).build());
    when(priceMapper.toDomain(priceJpa2)).thenReturn(Price.builder().productId("productId").brandId("brandId")
        .startDate(Timestamp.from(yesterday1).toLocalDateTime()).endDate(Timestamp.from(tomorrow1).toLocalDateTime())
        .priceList("priceList2").priority(Integer.valueOf("2")).finalPrice(Float.valueOf("3")).build());

    // Then
    List<Price> result = priceRespositoryImpl.findApplicatedPriceBy("productId", "brandId", LocalDateTime.now());

    assertFalse(result.isEmpty());
    assertEquals(prices.size(), result.size());

    PriceJpa priceJpa0 = prices.get(0);
    Price price = result.get(0);

    assertEquals(priceJpa0.getBrandId(), price.getBrandId());
    assertEquals(priceJpa0.getProductId(), price.getProductId());
    assertEquals(priceJpa0.getPrice(), price.getFinalPrice());
    assertEquals(priceJpa0.getPriceList(), price.getPriceList());
    assertEquals(priceJpa0.getPriority(), price.getPriority());
    assertEquals(priceJpa0.getStartDate().toLocalDateTime(), price.getStartDate());
    assertEquals(priceJpa0.getEndDate().toLocalDateTime(), price.getEndDate());
  }
}
