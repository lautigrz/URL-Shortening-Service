package com.shortening.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class URLResponseDTO {

    private String url;
    private String shortUrl;
    private Integer accessCount;


}
