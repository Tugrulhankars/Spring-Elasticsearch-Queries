package com.tugrulhan.elasticsearch_medium1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)

public class ProductFeature {

    private int Width;
    private int Height;
    private Color color;
}
