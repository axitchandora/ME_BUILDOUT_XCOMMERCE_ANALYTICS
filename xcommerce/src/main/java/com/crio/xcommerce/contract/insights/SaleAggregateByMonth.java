
package com.crio.xcommerce.contract.insights;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class SaleAggregateByMonth {
  private Integer month;
  private Double sales;
  // public Integer getMonth() {
  //   return month;
  // }
  // public Double getSales() {
  //   return sales;
  // }
  // public SaleAggregateByMonth(Integer month, Double sales) {
  //   this.month = month;
  //   this.sales = sales;
  // }
  
}

