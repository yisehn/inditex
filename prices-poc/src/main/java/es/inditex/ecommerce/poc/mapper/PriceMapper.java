package es.inditex.ecommerce.poc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.inditex.ecommerce.poc.adapter.repository.entity.PriceJpa;
import es.inditex.ecommerce.poc.domain.model.Price;

@Mapper(componentModel = "spring")
public interface PriceMapper {

  @Mapping(source = "price", target = "finalPrice")
  Price toDomain(PriceJpa price);
}
