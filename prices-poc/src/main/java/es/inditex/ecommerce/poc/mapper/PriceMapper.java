package es.inditex.ecommerce.poc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.inditex.ecommerce.poc.domain.Price;
import es.inditex.ecommerce.poc.entity.PriceJpa;

@Mapper(componentModel = "spring")
public interface PriceMapper {

  @Mapping(source = "price", target = "finalPrice")
  Price toDomain(PriceJpa price);
}
