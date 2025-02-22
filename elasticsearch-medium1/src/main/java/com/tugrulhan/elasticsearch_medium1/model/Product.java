package com.tugrulhan.elasticsearch_medium1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)

public class Product {
    private String id;
    private String name;
    private double price;
    private int Stock;
    private Date Created;
    private Optional<Date> Updated;
}
