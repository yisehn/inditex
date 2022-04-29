package es.inditex.ecommerce.poc.adapter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import es.inditex.ecommerce.poc.domain.Price;
import es.inditex.ecommerce.poc.entity.PriceJpa;
import es.inditex.ecommerce.poc.mapper.PriceMapper;
import es.inditex.ecommerce.poc.port.PriceRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PriceRespositoryImpl implements PriceRepository {

  final PriceMapper priceMapper;
  final PriceJpaRepository priceJpaRepository;

  @Override
  public List<Price> findApplicatedPriceBy(String productId, String brandId, LocalDateTime appDate) {

    List<PriceJpa> priceListFromDB = priceJpaRepository
        .findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(productId, brandId,
            Timestamp.valueOf(appDate), Timestamp.valueOf(appDate));

    return priceListFromDB.stream().map(x -> priceMapper.toDomain(x)).collect(Collectors.toList());
  }
}
