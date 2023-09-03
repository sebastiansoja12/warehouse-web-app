package com.warehouse.paypal.domain.port.primary;

import com.warehouse.paypal.domain.model.*;
import com.warehouse.paypal.domain.service.PaypalService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PaypalPortImpl implements PaypalPort {

    private final PaypalService paypalService;


    @Override
    public PaymentResponse payment(PaymentRequest request) {
        final PaymentInformation paymentInformation = paypalService.payment(request);
        return PaymentResponse.builder()
                .createTime(paymentInformation.getCreateTime())
                .paymentMethod(paymentInformation.getPaymentMethod())
                .link(new Link(paymentInformation.getPaymentUrl()))
                .failureReason(paymentInformation.getFailureReason())
                .build();
    }

    public PaymentUpdateResponse update(PaymentUpdateRequest paymentUpdateRequest) {
        return paypalService.update(paymentUpdateRequest);
    }
    
}
