package es.inditex.ecommerce.poc.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import es.inditex.ecommerce.poc.domain.model.Price;
import es.inditex.ecommerce.poc.port.service.PriceService;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  PriceService priceServiceImpl;

  @ParameterizedTest
  @CsvSource({ "2020-06-14T10:00:00,35455,1, 1, 35.50", "2020-06-14T16:00:00,35455,1, 2, 25.45",
      "2020-06-14T21:00:00,35455,1, 1, 35.50", "2020-06-15T10:00:00,35455,1, 3, 30.50",
      "2020-06-16T21:00:00,35455,1, 4, 38.95" })

  void whenExpectedParametersThenReturnOk(String applicationDate, String prodID, String brandID,
      String expectedPriceList, float expectedPrice) throws Exception {

    when(
        priceServiceImpl.getApplicatedPriceByDateProductAndBrand(LocalDateTime.parse(applicationDate), prodID, brandID))
            .thenReturn(Optional.of(Price.builder().priceList(expectedPriceList).finalPrice(expectedPrice).build()));

    MvcResult mvcResult = mockMvc.perform(get("/v1/ecommerce/prices").param("application_date", applicationDate)
        .param("product_id", prodID).param("brand_id", brandID)).andDo(print()).andExpect(status().isOk()).andReturn();

    String content = mvcResult.getResponse().getContentAsString();
    ObjectMapper objectMapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    objectMapper.registerModule(new JavaTimeModule());

    Price response = objectMapper.readValue(content, Price.class);

    assertEquals(expectedPriceList, response.getPriceList());
    assertEquals(expectedPrice, response.getFinalPrice());
  }

  @Test
  void whenBrandDoesNotExistThenReturnNotFound() {
    try {
      when(priceServiceImpl.getApplicatedPriceByDateProductAndBrand(Mockito.any(LocalDateTime.class),
          Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.empty());

      mockMvc
          .perform(get("/v1/ecommerce/prices").param("application_date", "2020-06-15T10:21:55")
              .param("product_id", "35455").param("brand_id", "2"))
          .andDo(print()).andExpect(status().isNotFound()).andReturn();

    } catch (Exception exc) {
      fail();
    }
  }

  @Test
  void whenRuntimeExceptionThenInternalServerError() {
    try {
      when(priceServiceImpl.getApplicatedPriceByDateProductAndBrand(Mockito.any(LocalDateTime.class),
          Mockito.anyString(), Mockito.anyString())).thenThrow(NullPointerException.class);

      mockMvc
          .perform(get("/v1/ecommerce/prices").param("application_date", "2020-06-15T10:21:55")
              .param("product_id", "35455").param("brand_id", "2"))
          .andDo(print()).andExpect(status().isInternalServerError()).andReturn();

    } catch (Exception exc) {
      fail();
    }
  }
}