package com.wen.config;

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
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
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
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class ElasticSearchUtils {
    @Autowired
    @Qualifier("restHighLevelClient")
    RestHighLevelClient client;
    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchUtils.class);

    /**
     * 索引创建
     * @param index 索引名
     */
    public String createIndex(String index) {
        //创建索引创建请求
        CreateIndexRequest request = new CreateIndexRequest(index);
        //执行请求,获得请求后的响应
        CreateIndexResponse createIndexResponse = null;
        try {
            createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            logger.info("未完成索引创建");
        }
        return String.valueOf(createIndexResponse);
    }


    /**
     * 测试索引是否存在
     * @param index 索引名
     * @return true false
     * @throws IOException
     */
    public boolean existIndex(String index) throws IOException {
        //创建索引存在请求
        GetIndexRequest request = new GetIndexRequest(index);
        //执行请求,获得请求后的响应
        boolean createIndexResponse = client.indices().exists(request, RequestOptions.DEFAULT);
        return createIndexResponse;
    }

    /**
     * 删除索引
     * @param index 索引名
     * @throws IOException
     */
    public boolean delIndex(String index) throws IOException {
        //创建索引删除请求
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        //执行请求,获得请求后的响应
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
        return delete.isAcknowledged();//true or false
    }


    /**
     * 添加文档
     * @param index 索引名、表名
     * @param document 对象
     * @throws IOException
     */
    public String addDocument(String index,String id,Object document,Integer timeout) throws IOException {
        //创建请求
        IndexRequest request=new IndexRequest(index);
        request.id(id);
        //设置规则
        request.timeout(TimeValue.timeValueSeconds(timeout));//一秒钟
        //将数据放入请求
        request.source(JSON.toJSONString(document), XContentType.JSON);
        //客户端发送请求,获取响应结果
        IndexResponse result = client.index(request, RequestOptions.DEFAULT);
        return result.status().toString();//CREATED or UPDATED
    }


    /**
     * 获取文档，判断是否存在
     * @param index 表名
     * @param id
     * @return
     * @throws IOException
     */
    public boolean existDocument(String index , String id) throws IOException {
        GetRequest request=new GetRequest(index,id);
        //不获取返回的 _source 上下文
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");
        return client.exists(request, RequestOptions.DEFAULT);
    }


    /**
     * 获取文档
     * @param index 表名索引名
     * @param id id
     * @throws IOException
     */
    public String getDocument(String index,String id) throws IOException {
        GetRequest request=new GetRequest(index,id);
        GetResponse documentFields = client.get(request, RequestOptions.DEFAULT);
        return documentFields.getSourceAsString();//打印文档内容
    }


    /**
     * 更新文档
     * @param index 表名
     * @param id id
     * @param document 新内容
     * @param timeout 允许时间长度
     * @throws IOException
     */
    public String updateDocument(String index,String id,Object document,Integer timeout) throws IOException {
        UpdateRequest request=new UpdateRequest(index,id);
        request.timeout(timeout+"s");
        UpdateRequest doc = request.doc(JSON.toJSONString(document), XContentType.JSON);
        UpdateResponse update = client.update(doc, RequestOptions.DEFAULT);
        return update.status().toString();//打印
    }

    /**
     * 删除文档
      * @param index 索引名
     * @param id id
     * @param timeout 超时时间
     * @throws IOException
     */
    public void delDocument(String index,String id,Integer timeout) throws IOException {
        DeleteRequest request=new DeleteRequest(index,id);
        request.timeout(timeout+"s");
        DeleteResponse delete = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.status());
    }

    /**
     *批量插入数据
     * @param index 索引
     * @param id id
     * @param document 内容
     * @return 返回状态
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
                    .id(id) //数据库id
                    .source(JSON.toJSONString(users.get(i)))
            );

        }
        BulkResponse bulk = client.bulk(request, RequestOptions.DEFAULT);
        return bulk.status().toString();
    }

    /**
     * 精确匹配
     * @param index 索引名
     * @param key 键
     * @param value 值
     * @param highlight 高亮字段
     * @param highlighttitle 高亮字段显示位置
     * @return list集合，需要在前台进行v-html解析
     * @throws IOException
     */
    public List termSearch(String index,String key,String value,String highlight,String highlighttitle) throws IOException {
        //创建请求
        SearchRequest request=new SearchRequest(index);
        //构建搜索条件
        SearchSourceBuilder source = new SearchSourceBuilder();
        //使用queryBulider快速匹配
        /**
         * QueryBuilders
         *  termQuery 精确匹配
         *  matchAllQuery 匹配所有
         */
        TermQueryBuilder term = QueryBuilders.termQuery(key,value);
        source.query(term);
        source.timeout(new TimeValue(60, TimeUnit.SECONDS));

        HighlightBuilder highlightBuilder=new HighlightBuilder();
        highlightBuilder.field(highlight);//要高亮的字段
        highlightBuilder.requireFieldMatch(false);//是否需要多个高亮
        highlightBuilder.preTags("<span style='color:blue'>");//前缀
        highlightBuilder.postTags("</span>");//后缀
        source.highlighter(highlightBuilder);//高亮

        request.source(source);
        SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = search.getHits();//所有的结果封装在这里

        return this.HitList(hits,highlighttitle);
    }
    /**
     * 分页查询+精确匹配
     * @param index 索引名
     * @param key 键
     * @param value 值
     * @param highlight 高亮字段
     * @param highlighttitle 高亮字段显示位置
     * @return list集合，需要在前台进行v-html解析
     * @throws IOException
     */
    public List termSearch(String index,String key,String value,Integer from,Integer size,String highlight,String highlighttitle) throws IOException {
        //创建请求
        SearchRequest request=new SearchRequest(index);
        //构建搜索条件
        SearchSourceBuilder source = new SearchSourceBuilder();
        //使用queryBulider快速匹配
        /**
         * QueryBuilders
         *  termQuery 精确匹配
         *  matchAllQuery 匹配所有
         */
        TermQueryBuilder term = QueryBuilders.termQuery(key,value);
        source.query(term);
        source.timeout(new TimeValue(60, TimeUnit.SECONDS));

        HighlightBuilder highlightBuilder=new HighlightBuilder();
        highlightBuilder.field(highlight);//要高亮的字段
        highlightBuilder.requireFieldMatch(false);//是否需要多个高亮
        highlightBuilder.preTags("<span style='color:blue'>");//前缀
        highlightBuilder.postTags("</span>");//后缀
        source.highlighter(highlightBuilder);//高亮

        source.from(from);
        source.size(size);
        source.sort("sell");//根据销售排序

        request.source(source);
        SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = search.getHits();//所有的结果封装在这里

        return this.HitList(hits,highlighttitle);
    }





    /**
     * 普通不带高亮模糊匹配
     * @param index 索引名
     * @param key 键
     * @param value 值
     * @return list集合，需要在前台进行v-html解析
     * @throws IOException
     */
    public List matchSearch(String index,String key,String value) throws IOException {
        //创建请求
        SearchRequest request=new SearchRequest(index);
        //构建搜索条件
        SearchSourceBuilder source = new SearchSourceBuilder();
        //使用queryBulider快速匹配
        /**
         * QueryBuilders
         *  termQuery 精确匹配
         *  matchAllQuery 匹配所有
         */
        QueryBuilder term = QueryBuilders.matchQuery(key, value);
        //查询方式
        source.query(term);

        //设置时间
        source.timeout(new TimeValue(60, TimeUnit.SECONDS));

        request.source(source);
        SearchResponse search = client.search(request, RequestOptions.DEFAULT);

        SearchHits hits = search.getHits();//所有的结果封装在这里

        return this.HitList(hits,null);
    }
    /**
     * 高亮模糊匹配
     * @param index 索引名
     * @param key 键
     * @param value 值
     * @param highlight 高亮字段
     * @param highlighttitle 高亮字段显示位置
     * @return list集合，需要在前台进行v-html解析
     * @throws IOException
     */
    public List matchSearch(String index,String key,String value,String highlight,String highlighttitle) throws IOException {
        //创建请求
        SearchRequest request=new SearchRequest(index);
        //构建搜索条件
        SearchSourceBuilder source = new SearchSourceBuilder();
        //使用queryBulider快速匹配
        /**
         * QueryBuilders
         *  termQuery 精确匹配
         *  matchAllQuery 匹配所有
         */
        QueryBuilder term = QueryBuilders.matchQuery(key, value);
        //查询方式
        source.query(term);

        //设置时间
        source.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //高亮
        HighlightBuilder highlightBuilder=new HighlightBuilder();
        highlightBuilder.field(highlight);//要高亮的字段
        highlightBuilder.requireFieldMatch(false);//是否需要多个高亮
        highlightBuilder.preTags("<span style='color:red'>");//前缀
        highlightBuilder.postTags("</span>");//后缀
        source.highlighter(highlightBuilder);//高亮

        //limit
        source.from(0);
        source.size(20);

        request.source(source);
        SearchResponse search = client.search(request, RequestOptions.DEFAULT);

        SearchHits hits = search.getHits();//所有的结果封装在这里

        return this.HitList(hits,highlighttitle);
    }

    /**
     * 带分页的模糊匹配
     * @param index 索引名
     * @param key 键
     * @param value 值
     * @param highlight 高亮字段
     * @param highlighttitle 高亮字段显示位置
     * @return list集合，需要在前台进行v-html解析
     * @throws IOException
     */
    public List matchSearch(String index,String key,String value,Integer from,Integer size,String highlight,String highlighttitle) throws IOException {
        //创建请求
        SearchRequest request=new SearchRequest(index);
        //构建搜索条件
        SearchSourceBuilder source = new SearchSourceBuilder();
        //使用queryBulider快速匹配
        /**
         * QueryBuilders
         *  termQuery 精确匹配
         *  matchAllQuery 匹配所有
         */
        QueryBuilder term = QueryBuilders.matchQuery(key, value);
        //查询方式
        source.query(term);

        //设置时间
        source.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //高亮
        HighlightBuilder highlightBuilder=new HighlightBuilder();
        highlightBuilder.field(highlight);//要高亮的字段
        highlightBuilder.requireFieldMatch(false);//是否需要多个高亮
        highlightBuilder.preTags("<span style='color:red'>");//前缀
        highlightBuilder.postTags("</span>");//后缀
        source.highlighter(highlightBuilder);//高亮

        //limit
        source.from(from);
        source.size(size);
        source.sort("sell");//排序

        request.source(source);
        SearchResponse search = client.search(request, RequestOptions.DEFAULT);

        SearchHits hits = search.getHits();//所有的结果封装在这里

        return this.HitList(hits,highlighttitle);
    }



    /**
     * 处理查询结果
     * @param hits 结果集
     * @param highlighttitle 高亮字段的位置
     * @return
     */
    private List<Map<String,Object>> HitList(SearchHits hits,String highlighttitle){
        List<Map<String,Object>> list=new ArrayList<>();
        for (SearchHit hit : hits.getHits()) {
            //获取高亮字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            //显示的地方
            HighlightField title = highlightFields.get(highlighttitle);
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //解析高亮字段,将没有高亮字段替换高亮的字段
            if (title != null){
                Text[] fragments = title.fragments();
                String newtitle = "";
                for (Text fragment : fragments) {
                    newtitle += fragment;
                }
                sourceAsMap.put(highlighttitle,newtitle);//替换成高亮字段
            }

            list.add(sourceAsMap);//前端使用v-html解析即可

        }
        return list;
    }


}
