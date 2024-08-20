package com.example.demo.controllers;

import lombok.Data;

import java.util.List;

@Data
public class OrderMessage {
    List<Object> products;
    String totalPrice;
    String queryId;
}
