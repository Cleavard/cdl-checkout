package com.cdl.checkout.rcsolution;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ItemPrice {
    @Getter private double price;
    @Getter private int specialAmount = 0;
    @Getter private double specialPrice;

}

