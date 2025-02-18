package com.eureka.spartaonetoone.domain.payment.application.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentSearchDto {
    private String bank;
    private String state;
    private Integer price;
}
