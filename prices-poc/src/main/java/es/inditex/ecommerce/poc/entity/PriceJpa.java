package es.inditex.ecommerce.poc.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "PRICES")
public class PriceJpa {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", nullable = false)
  private Long id;

  @Column(name = "BRAND_ID", nullable = false)
  private String brandId;

  @Column(name = "START_DATE", nullable = false)
  private Timestamp startDate;

  @Column(name = "END_DATE", nullable = false)
  private Timestamp endDate;

  @Column(name = "PRICE_LIST", nullable = false)
  private String priceList;

  @Column(name = "PRODUCT_ID", nullable = false)
  private String productId;

  @Column(name = "PRIORITY", nullable = false)
  private Integer priority;

  @Column(name = "PROD_PRICE", nullable = false)
  private Float price;

  @Column(name = "CURR", nullable = false)
  private String curr;
}
