package com.example.demo.controllers;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class OrderMessage {
    List<Object> products;
    String totalPrice;
    String queryId;

    Map<String, String> shippingInfo;
    Map<String, String> additionalInfo;
}
