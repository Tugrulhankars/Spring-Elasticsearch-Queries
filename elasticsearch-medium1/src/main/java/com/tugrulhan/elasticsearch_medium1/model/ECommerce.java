package com.tugrulhan.elasticsearch_medium1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.Date;

@Setter
@Getter
@Document(indexName = "ecommerce")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ECommerce {
    private String id;

    @JsonProperty("customer_first_name")
    private String customerFirstName;

    @JsonProperty("customer_last_name")
    private String customerLastName;

    @JsonProperty("customer_full_name")
    private String customerFullName;

    @JsonProperty("taxful_total_price")
    private double taxfulTotalPrice;

    private String[] category;

    @JsonProperty("order_id")
    private int orderId;

    @JsonProperty("order_date")
    private Date orderDate;

    private Product[] products;


}
