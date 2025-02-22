package com.tugrulhan.elasticsearch_medium1.controller;

import com.tugrulhan.elasticsearch_medium1.model.ECommerce;
import com.tugrulhan.elasticsearch_medium1.repository.ECommerceRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/ecommerce")
@Tag(name = "ECommerce")
public class ECommerceController {

    private final ECommerceRepository eCommerceRepository;

    public ECommerceController(ECommerceRepository eCommerceRepository) {
        this.eCommerceRepository = eCommerceRepository;
    }


    @GetMapping("/search")
    @Operation(summary = "ECommerce TermQuery")
    public ResponseEntity<ArrayList<ECommerce>> termQuery(@RequestParam String name) throws IOException {
        var response=eCommerceRepository.termQuery(name);
        return ResponseEntity.ok((ArrayList<ECommerce>) response);

    }

    @GetMapping("/search/prefix")
    @Operation(summary = "ECommerce PrefixQuery")
    public ResponseEntity<ArrayList<ECommerce>> prefixQuery() throws IOException {
        var response=eCommerceRepository.prefixQuery("Mary");
        return ResponseEntity.ok((ArrayList<ECommerce>) response);
    }

    @GetMapping("/search/wildcard")
    @Operation(summary = "ECommerce WildCardQuery")
    public ResponseEntity<ArrayList<ECommerce>> wildCardQuery(@RequestParam String name) throws IOException {
        return ResponseEntity.ok(eCommerceRepository.wildCardQuery(name));
    }

    @GetMapping("/search/fuzzy")
    @Operation(summary = "ECommerce FuzzyQuery")
    public  ResponseEntity<ArrayList<ECommerce>> fuzzyQuery(@RequestParam String seacrh) throws IOException {
        return ResponseEntity.ok(eCommerceRepository.fuzzyQuery(seacrh));
    }

    @GetMapping("/search/range")
    @Operation(summary = "ECommerce RangeQuery")
    public ResponseEntity<ArrayList<ECommerce>> rangeQuery(@RequestParam double fromPrice,@RequestParam double toPrice) throws IOException {
        return ResponseEntity.ok(eCommerceRepository.rangeQuery(fromPrice,toPrice));
    }

    @GetMapping("/search/matchAll")
    @Operation(summary = "ECommerce MatchAllQuery")
    public ResponseEntity<ArrayList<ECommerce>> matchAllQuery() throws IOException {
        return ResponseEntity.ok(eCommerceRepository.matchAllQuery());
    }

    @GetMapping("/search/compound")
    @Operation(summary = "ECommerce CompoundQuery")
    public ResponseEntity<ArrayList<ECommerce>> compoundQueryExampleOne() throws IOException {
        return ResponseEntity.ok(eCommerceRepository.compoundQueryExampleOne("London", Double.parseDouble("taxfulTotalPrice"),"furniture","IKEA"));
    }
}
