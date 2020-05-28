package dev.amaro.androidrx;

import java.math.BigDecimal;

public class AccountVO {

    private String customerName;
    private BigDecimal balance;


    public String customerName() {
        return customerName;
    }

    public BigDecimal balance() {
        return balance;
    }
}
