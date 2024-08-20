package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RunResult {

    Double gst;
    Double energy;
    String owner;
    String token;

}

