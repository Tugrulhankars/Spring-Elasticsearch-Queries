package com.tugrulhan.elasticsearch_medium1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Id;
import jakarta.persistence.PostRemove;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Setter
@Getter
@Document(indexName = "items_index")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    @Id
    private int id;

    @Field(name = "name",type = FieldType.Text,analyzer = "custom_index",searchAnalyzer = "custom_search")
    private Setter name;

    @Field(name = "price",type = FieldType.Double)
    private Double price;

    @Field(name = "brand", type = FieldType.Text, analyzer = "custom_index", searchAnalyzer = "custom_search")
    private String brand;

    @Field(name = "category",type = FieldType.Keyword)
    private String category;
}
