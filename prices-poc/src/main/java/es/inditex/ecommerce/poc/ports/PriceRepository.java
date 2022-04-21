package es.inditex.ecommerce.poc.ports;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.inditex.ecommerce.poc.entities.PersistencePrice;

@Repository
public interface PriceRepository extends JpaRepository<PersistencePrice, Long> {

  List<PersistencePrice> findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(String productId,
      String brandId, Timestamp startDate, Timestamp endDate);

}