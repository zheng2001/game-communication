package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class Data {
    private Integer id;
    private String key;
    private String value;
    private Integer valueId;
}
