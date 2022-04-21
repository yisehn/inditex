package es.inditex.ecommerce.poc.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.inditex.ecommerce.poc.entities.PersistencePrice;
import es.inditex.ecommerce.poc.model.Price;
import es.inditex.ecommerce.poc.ports.PriceRepository;
import es.inditex.ecommerce.poc.ports.PriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

  final PriceRepository priceRepository;

  public Optional<Price> getPriceInfoByDateProductAndBrand(LocalDateTime applicationDate, String productId,
      String brandId) {

    Optional<Price> result = Optional.empty();

    List<PersistencePrice> priceListFromDB = priceRepository
        .findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(productId, brandId,
            Timestamp.valueOf(applicationDate), Timestamp.valueOf(applicationDate));
    log.info("PersistencePriceList size: " + priceListFromDB.size(), this);

    if (!priceListFromDB.isEmpty()) {
      PersistencePrice persistencePrice = (priceListFromDB.size() > 1)
          ? priceListFromDB.stream().max(Comparator.comparing(PersistencePrice::getPriority)).get()
          : priceListFromDB.get(0);

      result = Optional
          .of(Price.builder().productId(productId).brandId(brandId).priceList(persistencePrice.getPriceList())
              .finalPrice(persistencePrice.getPrice()).initialDate(persistencePrice.getStartDate().toLocalDateTime())
              .endDate(persistencePrice.getEndDate().toLocalDateTime()).build());
    }
    return result;
  }
}
