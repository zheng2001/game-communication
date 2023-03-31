package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Banner {
    private Integer id;
    private String bannerImg;
    private Integer order;
    private Integer bannerStatus;
}
