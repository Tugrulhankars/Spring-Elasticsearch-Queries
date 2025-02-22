package com.tugrulhan.elasticsearch_medium1.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import co.elastic.clients.json.JsonData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tugrulhan.elasticsearch_medium1.model.ECommerce;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class ECommerceRepository {

    private final ElasticsearchClient elasticsearchClient;
    private final String indexName="kibana_sample_data_ecommerce";

    public ECommerceRepository(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    public ArrayList<ECommerce> termQuery(String name) throws IOException {
        SearchResponse<JsonData> searchResponse = elasticsearchClient
                .search(s -> s.index(indexName)
                                .query(q ->
                                        q.term(t ->
                                        t.field("customer_first_name")
                                                .value(name)
                                        )),
                        JsonData.class);


        ArrayList<ECommerce> eCommerceList =
                (ArrayList<ECommerce>) searchResponse.hits().hits()
                .stream()
                .map(hit -> parseToECommerce(hit.source()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return eCommerceList;
    }

    public ArrayList<ECommerce> prefixQuery(String customerFirstName) throws IOException {
        SearchResponse<ECommerce> searchResponse=elasticsearchClient.
                search(s->s.index(indexName).
                query(q->q.prefix(p->
                        p.field("customer_full_name.keyword")
                                .value(customerFirstName))),ECommerce.class);
        TotalHits hits=searchResponse.hits().total(); //Elasticsearch arama sonucunda dönen toplam belge  sayısını alır.

        boolean isExactResult = hits.relation() == TotalHitsRelation.Eq;
        ArrayList<ECommerce> eCommerceList = new ArrayList<>();

        List<Hit<ECommerce>> hits1 = searchResponse.hits().hits();//Elasticsearch'ten dönen sonuçların içeriğini (Hit nesneleri olarak) almak.
        for (Hit<ECommerce> hit: hits1) {
            ECommerce product = hit.source();//Her bir Hit içindeki asıl belge  nesnesine  ulaşır.
            eCommerceList.add(product);
        }

        return eCommerceList;
    }

    public ArrayList<ECommerce> rangeQuery(double fromPrice,double toPrice) throws IOException {
         SearchResponse<ECommerce> searchResponse=elasticsearchClient
                 .search(s->s.index(indexName)
                 .query(q->
                         q.range(r->
                                 r.field("taxful_total_price")
                                         .gte(JsonData.of(fromPrice))
                                         .lte(JsonData.of(toPrice)))),ECommerce.class);
        TotalHits hits=searchResponse.hits().total();
        ArrayList<ECommerce> eCommerceList = new ArrayList<>();

        List<Hit<ECommerce>> ht=searchResponse.hits().hits();
        for (Hit<ECommerce> hit: ht){
            ECommerce product=hit.source();
            eCommerceList.add(product);
        }

        return eCommerceList;
    }

    public ArrayList<ECommerce> matchAllQuery() throws IOException {
        SearchResponse<ECommerce> searchResponse=elasticsearchClient.search(s->s.index(indexName)
                .query(q->q.matchAll(new MatchAllQuery.Builder().build())),ECommerce.class);

        ArrayList<ECommerce> eCommerceList = new ArrayList<>();
        List<Hit<ECommerce>> ht=searchResponse.hits().hits();
        for (Hit<ECommerce> hit: ht){
            ECommerce product=hit.source();
            eCommerceList.add(product);
        }
        return eCommerceList;
    }

    public ArrayList<ECommerce> paginationQuery(int page,int pageSize) throws IOException {
      int  pageFrom=(page-1)*pageSize;
        SearchResponse<ECommerce> searchResponse=elasticsearchClient
                .search(s->s.index(indexName)
                        .size(pageSize).from(pageFrom)
                        .query(q->q.matchAll(new MatchAllQuery.Builder().build())),ECommerce.class);
        TotalHits hits=searchResponse.hits().total();
        ArrayList<ECommerce> eCommerceList = new ArrayList<>();

        List<Hit<ECommerce>> ht=searchResponse.hits().hits();
        for (Hit<ECommerce> hit: ht){
            ECommerce product=hit.source();
            eCommerceList.add(product);
        }
        return eCommerceList;
    }




    public ArrayList<ECommerce> wildCardQuery(String name) throws IOException {
        SearchResponse<ECommerce> searchResponse=elasticsearchClient
                .search(s->s.index(indexName)
                        .query(q->
                                q.wildcard(w->
                                        w.field("customer_full_name.keyword")
                                                .value(name))),ECommerce.class);
        TotalHits hits=searchResponse.hits().total();
        ArrayList<ECommerce> eCommerceList = new ArrayList<>();

        List<Hit<ECommerce>> ht=searchResponse.hits().hits();
        for (Hit<ECommerce> hit: ht){
            ECommerce product=hit.source();
            eCommerceList.add(product);
        }

        return eCommerceList;
    }

    public  ArrayList<ECommerce>  fuzzyQuery(String seacrh) throws IOException {

        SearchResponse<ECommerce> searchResponse=elasticsearchClient
                .search(s->s.index(indexName)
                        .query(q->q.
                                fuzzy(f->f.
                                        field("customer_first_name.keyword")
                                        .value(seacrh).fuzziness("2")))
                        ,ECommerce.class);
        TotalHits hits=searchResponse.hits().total();
        ArrayList<ECommerce> eCommerceList = new ArrayList<>();

        List<Hit<ECommerce>> ht=searchResponse.hits().hits();
        for (Hit<ECommerce> hit: ht){
            ECommerce product=hit.source();
            eCommerceList.add(product);
        }

        return eCommerceList;

    }

    public ArrayList<ECommerce> matchQueryFullText(String categoryName) throws IOException {
        SearchResponse<ECommerce> searchResponse=elasticsearchClient
                .search(s->s.index(indexName)
                .size(10)
                        .query(q->
                                q.match(m->
                                        m.field("category")
                                                .query(categoryName))),ECommerce.class);

        ArrayList<ECommerce> eCommerceList = new ArrayList<>();
        List<Hit<ECommerce>> ht=searchResponse.hits().hits();
        for (Hit<ECommerce> hit: ht){
            ECommerce product=hit.source();
            eCommerceList.add(product);
        }
        return eCommerceList;
    }

    public ArrayList<ECommerce> multiMatchQueryFullText(String name) throws IOException {

        SearchResponse<ECommerce> searchResponse=elasticsearchClient
                .search(s->s.index(indexName)
                        .size(10)
                        .query(q->
                                q.multiMatch(m->
                                        m.fields("customer_full_name","customer_last_name")
                                                .query(name))),ECommerce.class);
        TotalHits hits=searchResponse.hits().total();
        ArrayList<ECommerce> eCommerceList = new ArrayList<>();

        List<Hit<ECommerce>> ht=searchResponse.hits().hits();
        for (Hit<ECommerce> hit: ht){
            ECommerce product=hit.source();
            eCommerceList.add(product);
        }
        return eCommerceList;
    }



    public ArrayList<ECommerce> compoundQueryExampleOne(String customerFullName,double taxfulTotalPrice,String categoryName,String menufacturer) throws IOException {

        SearchResponse<ECommerce> searchResponse = elasticsearchClient.search(s ->
                        s.index(indexName)
                                .size(5)
                                .query(q ->
                                        q.bool(b ->
                                                b.must(QueryBuilders.term(t ->
                                                                t.field("customer_full_name.keyword")
                                                                        .value(customerFullName)))
                                                        .must(QueryBuilders.range(r ->
                                                                r.field("taxful_total_price")
                                                                        .lte(JsonData.of(taxfulTotalPrice))))
                                                        .must(QueryBuilders.term(t ->
                                                                t.field("category")
                                                                        .value(categoryName)))
                                                        .must(QueryBuilders.term(t ->
                                                                t.field("products.manufacturer")
                                                                        .value(menufacturer)))
                                        )
                                ),
                ECommerce.class
        );

        TotalHits hits=searchResponse.hits().total();
            ArrayList<ECommerce> eCommerceList = new ArrayList<>();
            List<Hit<ECommerce>> ht=searchResponse.hits().hits();
            for (Hit<ECommerce> hit: ht){
                ECommerce product=hit.source();
                eCommerceList.add(product);
            }
            return eCommerceList;

    }







    private final ObjectMapper objectMapper = new ObjectMapper();

    private ECommerce parseToECommerce(JsonData jsonData) {
        try {
            return objectMapper.convertValue(jsonData, ECommerce.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
