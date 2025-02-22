package com.tugrulhan.elasticsearch_medium1.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SearchRequestDto {

    private List<String> fieldName;
    private List<String> searchValue;
}
