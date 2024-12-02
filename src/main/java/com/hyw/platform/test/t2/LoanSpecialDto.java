package com.hyw.platform.test.t2;


import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class LoanSpecialDto {
    private Integer paymentNoBeg;
    private Integer paymentNoEnd;
    private BigDecimal paymentPrincipalSum;
    private BigDecimal paymentPrincipalMax;
    private BigDecimal paymentInterestSum;
    private BigDecimal paymentInterestMax;
}

