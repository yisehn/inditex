package es.inditex.ecommerce.poc.adapter.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.inditex.ecommerce.poc.adapter.repository.entity.PriceJpa;

@Repository
public interface PriceJpaRepository extends JpaRepository<PriceJpa, Long> {

  List<PriceJpa> findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(String productId,
      String brandId, Timestamp startDate, Timestamp endDate);

}