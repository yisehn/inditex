package es.inditex.ecommerce.poc.domain;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.inditex.ecommerce.poc.port.PriceRepository;
import es.inditex.ecommerce.poc.port.PriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

  final PriceRepository priceRepository;

  public Optional<Price> getApplicatedPriceByDateProductAndBrand(LocalDateTime applicationDate, String productId,
      String brandId) {

    Optional<Price> result = Optional.empty();

    List<Price> priceListFromPersitence = priceRepository.findApplicatedPriceBy(productId, brandId, applicationDate);
    log.info("PersistencePriceList size: " + priceListFromPersitence.size(), this);

    if (!priceListFromPersitence.isEmpty()) {
      result = Optional.of((priceListFromPersitence.size() > 1)
          ? priceListFromPersitence.stream().max(Comparator.comparing(Price::getPriority)).get()
          : priceListFromPersitence.get(0));
    }
    return result;
  }
}
