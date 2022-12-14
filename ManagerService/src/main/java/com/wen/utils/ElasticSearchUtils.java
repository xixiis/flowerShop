package com.wen.utils;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class ElasticSearchUtils {
    @Autowired
    @Qualifier("restHighLevelClient")
    RestHighLevelClient client;
    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchUtils.class);

    /**
     * ????????????
     * @param index ?????????
     */
    public String createIndex(String index) {
        //????????????????????????
        CreateIndexRequest request = new CreateIndexRequest(index);
        //????????????,????????????????????????
        CreateIndexResponse createIndexResponse = null;
        try {
            createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            logger.info("?????????????????????");
        }
        return String.valueOf(createIndexResponse);
    }


    /**
     * ????????????????????????
     * @param index ?????????
     * @return true false
     * @throws IOException
     */
    public boolean existIndex(String index) throws IOException {
        //????????????????????????
        GetIndexRequest request = new GetIndexRequest(index);
        //????????????,????????????????????????
        boolean createIndexResponse = client.indices().exists(request, RequestOptions.DEFAULT);
        return createIndexResponse;
    }

    /**
     * ????????????
     * @param index ?????????
     * @throws IOException
     */
    public boolean delIndex(String index) throws IOException {
        //????????????????????????
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        //????????????,????????????????????????
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
        return delete.isAcknowledged();//true or false
    }


    /**
     * ????????????
     * @param index ??????????????????
     * @param document ??????
     * @throws IOException
     */
    public String addDocument(String index,String id,Object document,Integer timeout) throws IOException {
        //????????????
        IndexRequest request=new IndexRequest(index);
        request.id(id);
        //????????????
        request.timeout(TimeValue.timeValueSeconds(timeout));//?????????
        //?????????????????????
        request.source(JSON.toJSONString(document), XContentType.JSON);
        //?????????????????????,??????????????????
        IndexResponse result = client.index(request, RequestOptions.DEFAULT);
        return result.status().toString();//CREATED or UPDATED
    }


    /**
     * ?????????????????????????????????
     * @param index ??????
     * @param id
     * @return
     * @throws IOException
     */
    public boolean existDocument(String index , String id) throws IOException {
        GetRequest request=new GetRequest(index,id);
        //?????????????????? _source ?????????
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");
        return client.exists(request, RequestOptions.DEFAULT);
    }


    /**
     * ????????????
     * @param index ???????????????
     * @param id id
     * @throws IOException
     */
    public String getDocument(String index,String id) throws IOException {
        GetRequest request=new GetRequest(index,id);
        GetResponse documentFields = client.get(request, RequestOptions.DEFAULT);
        return documentFields.getSourceAsString();//??????????????????
    }


    /**
     * ????????????
     * @param index ??????
     * @param id id
     * @param document ?????????
     * @param timeout ??????????????????
     * @throws IOException
     */
    public String updateDocument(String index,String id,Object document,Integer timeout) throws IOException {
        UpdateRequest request=new UpdateRequest(index,id);
        request.timeout(timeout+"s");
        UpdateRequest doc = request.doc(JSON.toJSONString(document), XContentType.JSON);
        UpdateResponse update = client.update(doc, RequestOptions.DEFAULT);
        return update.status().toString();//??????
    }

    /**
     * ????????????
      * @param index ?????????
     * @param id id
     * @param timeout ????????????
     * @throws IOException
     */
    public void delDocument(String index,String id,Integer timeout) throws IOException {
        DeleteRequest request=new DeleteRequest(index,id);
        request.timeout(timeout+"s");
        DeleteResponse delete = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.status());
    }

    /**
     *??????????????????
     * @param index ??????
     * @param id id
     * @param document ??????
     * @return ????????????
     * @throws IOException
     */
    public String BulkDocument(String index,String id,Object document) throws IOException {
        BulkRequest request=new BulkRequest();
        request.timeout("10s");
        List<Object> users = new ArrayList<>();
        users.add(document);
        for (int i = 0; i < users.size(); i++) {
            request.add(
                    new IndexRequest(index)
                    .id(id) //?????????id
                    .source(JSON.toJSONString(users.get(i)))
            );

        }
        BulkResponse bulk = client.bulk(request, RequestOptions.DEFAULT);
        return bulk.status().toString();
    }

    /**
     * ????????????
     * @param index ?????????
     * @param key ???
     * @param value ???
     * @param highlight ????????????
     * @param highlighttitle ????????????????????????
     * @return list??????????????????????????????v-html??????
     * @throws IOException
     */
    public List termSearch(String index,String key,String value,String highlight,String highlighttitle) throws IOException {
        //????????????
        SearchRequest request=new SearchRequest(index);
        //??????????????????
        SearchSourceBuilder source = new SearchSourceBuilder();
        //??????queryBulider????????????
        /**
         * QueryBuilders
         *  termQuery ????????????
         *  matchAllQuery ????????????
         */
        TermQueryBuilder term = QueryBuilders.termQuery(key,value);
        source.query(term);
        source.timeout(new TimeValue(60, TimeUnit.SECONDS));

        HighlightBuilder highlightBuilder=new HighlightBuilder();
        highlightBuilder.field(highlight);//??????????????????
        highlightBuilder.requireFieldMatch(false);//????????????????????????
        highlightBuilder.preTags("<span style='color:blue'>");//??????
        highlightBuilder.postTags("</span>");//??????
        source.highlighter(highlightBuilder);//??????

        request.source(source);
        SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = search.getHits();//??????????????????????????????

        return this.HitList(hits,highlighttitle);
    }
    /**
     * ????????????+????????????
     * @param index ?????????
     * @param key ???
     * @param value ???
     * @param highlight ????????????
     * @param highlighttitle ????????????????????????
     * @return list??????????????????????????????v-html??????
     * @throws IOException
     */
    public List termSearch(String index,String key,String value,Integer from,Integer size,String highlight,String highlighttitle) throws IOException {
        //????????????
        SearchRequest request=new SearchRequest(index);
        //??????????????????
        SearchSourceBuilder source = new SearchSourceBuilder();
        //??????queryBulider????????????
        /**
         * QueryBuilders
         *  termQuery ????????????
         *  matchAllQuery ????????????
         */
        TermQueryBuilder term = QueryBuilders.termQuery(key,value);
        source.query(term);
        source.timeout(new TimeValue(60, TimeUnit.SECONDS));

        HighlightBuilder highlightBuilder=new HighlightBuilder();
        highlightBuilder.field(highlight);//??????????????????
        highlightBuilder.requireFieldMatch(false);//????????????????????????
        highlightBuilder.preTags("<span style='color:blue'>");//??????
        highlightBuilder.postTags("</span>");//??????
        source.highlighter(highlightBuilder);//??????

        source.from(from);
        source.size(size);

        request.source(source);
        SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = search.getHits();//??????????????????????????????

        return this.HitList(hits,highlighttitle);
    }





    /**
     * ??????????????????????????????
     * @param index ?????????
     * @param key ???
     * @param value ???
     * @return list??????????????????????????????v-html??????
     * @throws IOException
     */
    public List matchSearch(String index,String key,String value) throws IOException {
        //????????????
        SearchRequest request=new SearchRequest(index);
        //??????????????????
        SearchSourceBuilder source = new SearchSourceBuilder();
        //??????queryBulider????????????
        /**
         * QueryBuilders
         *  termQuery ????????????
         *  matchAllQuery ????????????
         */
        QueryBuilder term = QueryBuilders.matchQuery(key, value);
        //????????????
        source.query(term);

        //????????????
        source.timeout(new TimeValue(60, TimeUnit.SECONDS));

        request.source(source);
        SearchResponse search = client.search(request, RequestOptions.DEFAULT);

        SearchHits hits = search.getHits();//??????????????????????????????

        return this.HitList(hits,null);
    }
    /**
     * ??????????????????
     * @param index ?????????
     * @param key ???
     * @param value ???
     * @param highlight ????????????
     * @param highlighttitle ????????????????????????
     * @return list??????????????????????????????v-html??????
     * @throws IOException
     */
    public List matchSearch(String index,String key,String value,String highlight,String highlighttitle) throws IOException {
        //????????????
        SearchRequest request=new SearchRequest(index);
        //??????????????????
        SearchSourceBuilder source = new SearchSourceBuilder();
        //??????queryBulider????????????
        /**
         * QueryBuilders
         *  termQuery ????????????
         *  matchAllQuery ????????????
         */
        QueryBuilder term = QueryBuilders.matchQuery(key, value);
        //????????????
        source.query(term);

        //????????????
        source.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //??????
        HighlightBuilder highlightBuilder=new HighlightBuilder();
        highlightBuilder.field(highlight);//??????????????????
        highlightBuilder.requireFieldMatch(false);//????????????????????????
        highlightBuilder.preTags("<span style='color:red'>");//??????
        highlightBuilder.postTags("</span>");//??????
        source.highlighter(highlightBuilder);//??????

        //limit
        source.from(0);
        source.size(20);

        request.source(source);
        SearchResponse search = client.search(request, RequestOptions.DEFAULT);

        SearchHits hits = search.getHits();//??????????????????????????????

        return this.HitList(hits,highlighttitle);
    }

    /**
     * ????????????????????????
     * @param index ?????????
     * @param key ???
     * @param value ???
     * @param highlight ????????????
     * @param highlighttitle ????????????????????????
     * @return list??????????????????????????????v-html??????
     * @throws IOException
     */
    public List matchSearch(String index,String key,Integer from,Integer size,String value,String highlight,String highlighttitle) throws IOException {
        //????????????
        SearchRequest request=new SearchRequest(index);
        //??????????????????
        SearchSourceBuilder source = new SearchSourceBuilder();
        //??????queryBulider????????????
        /**
         * QueryBuilders
         *  termQuery ????????????
         *  matchAllQuery ????????????
         */
        QueryBuilder term = QueryBuilders.matchQuery(key, value);
        //????????????
        source.query(term);

        //????????????
        source.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //??????
        HighlightBuilder highlightBuilder=new HighlightBuilder();
        highlightBuilder.field(highlight);//??????????????????
        highlightBuilder.requireFieldMatch(false);//????????????????????????
        highlightBuilder.preTags("<span style='color:red'>");//??????
        highlightBuilder.postTags("</span>");//??????
        source.highlighter(highlightBuilder);//??????

        //limit
        source.from(from);
        source.size(size);

        request.source(source);
        SearchResponse search = client.search(request, RequestOptions.DEFAULT);

        SearchHits hits = search.getHits();//??????????????????????????????

        return this.HitList(hits,highlighttitle);
    }



    /**
     * ??????????????????
     * @param hits ?????????
     * @param highlighttitle ?????????????????????
     * @return
     */
    private List<Map<String,Object>> HitList(SearchHits hits,String highlighttitle){
        List<Map<String,Object>> list=new ArrayList<>();
        for (SearchHit hit : hits.getHits()) {
            //??????????????????
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            //???????????????
            HighlightField title = highlightFields.get(highlighttitle);
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //??????????????????,??????????????????????????????????????????
            if (title != null){
                Text[] fragments = title.fragments();
                String newtitle = "";
                for (Text fragment : fragments) {
                    newtitle += fragment;
                }
                sourceAsMap.put(highlighttitle,newtitle);//?????????????????????
            }

            list.add(sourceAsMap);//????????????v-html????????????

        }
        return list;
    }


}
