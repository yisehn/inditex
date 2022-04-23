package es.inditex.ecommerce.poc.mapper;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import es.inditex.ecommerce.poc.handler.ApiError;

@Mapper(componentModel = "spring", imports = { LocalDateTime.class })
public interface ApiErrorMapper {

  @Mappings({ @Mapping(target = "timestamp", expression = "java(LocalDateTime.now().toString())"),
      @Mapping(source = "errorCode", target = "error"), @Mapping(source = "exc.message", target = "message") })
  ApiError toApiError(String errorCode, Exception exc, String path);

}
