package com.example.ProjectV2.dto.Customer;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PayDto {

    private Long orderId;
    private double amount;
    private String cardNumber;
    private String cvv2;
    private String expireDate;
    private String cartHoldersName;
    private String captcha;


    public boolean checkCardNumber(){
        return cardNumber.length()==16;
    }

}
