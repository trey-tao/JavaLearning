package com.trey.es;

import com.google.gson.GsonBuilder;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.CreateIndex;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.Field;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

import java.io.IOException;
import java.util.List;

/**
 * @Auther: trey_stao@163.com
 * @Date: 2018/5/25 14:56
 * @Description:
 */
public class ElasticsearchDemo {

    private static final String username = "elasticsearch";
    private static final String password = "123456";


    /**
     * 获取JestClient对象
     * @return
     */
    private static JestClient getJestClient() {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder("http://127.0.0.1:9200")
                .defaultCredentials(username,password)
                .gson(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create())
                .multiThreaded(true)
                .readTimeout(10000)
                .build());
        JestClient client = factory.getObject();
        return client;
    }

    /**
     * 创建索引
     * @param client
     * @throws IOException
     */
    public JestResult createIndex(JestClient client) throws IOException {
        String index = "stringyone";
        String expectedType1Maping =
                "\"_source\":{\"enabled\":false},\"properties\":{\"field1\":{\"type\":\"keyword\"}}";
        String settingsJson = "{\n" +
                "    \"settings\" : {\n" +
                "        \"number_of_shards\" : 8\n" +
                "    },\n" +
                "    \"mappings\" : {\"type1\": {" + expectedType1Maping + "}}" +
                "}";

        //此处settingsJson字符串就是settings 和 mappings
        CreateIndex createIndex = new CreateIndex.Builder(index)
                .settings(settingsJson)
                .build();

        JestResult result = client.execute(createIndex);
        return result;
    }

    /**
     * 带条件检索
     * 实现类似  select * from table where filed = queryString AND (... or条件字段 ...)
     * @param indexName   索引名
     * @param typeName    类型名
     * @param queryString 查询内容
     * @param field       字段对象，包括查询字段及查询字段的值
     * @param fieldForOrs 查询条件 or区分
     * @param client
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public JestResult index(String indexName, String typeName, String queryString, String field, List<Field> fieldForOrs, JestClient client, int pageNumber, int pageSize) {
        //声明一个SearchSourceBuilder对象，构造检索请求体
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        //构造查询哪个字段
        if(StringUtils.isEmpty(field)) {
            //没有检索条件，则全字段查询
            boolQueryBuilder = boolQueryBuilder.must(QueryBuilders.queryStringQuery(queryString));
        } else {
            boolQueryBuilder = boolQueryBuilder.must(QueryBuilders.matchQuery(field,queryString));
        }

        /*BoolQueryBuilder innerQueryBuilder = QueryBuilders.boolQuery();
        for(Field fieldValue : fieldForOrs) {
            innerQueryBuilder = innerQueryBuilder.should(QueryBuilders.termQuery(fieldValue.getFieldName(),fieldValue.getFieldValue()));
        }
        boolQueryBuilder = boolQueryBuilder.filter(innerQueryBuilder);
        */
        searchSourceBuilder.query(boolQueryBuilder);

        //设置高亮字段
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field(field);
        highlightBuilder.preTags("<em>").postTags("</em>");
        highlightBuilder.fragmentSize(200);
        searchSourceBuilder.highlighter(highlightBuilder);

        //设置分页
        searchSourceBuilder.from((pageNumber - 1) * pageSize);
        searchSourceBuilder.size(pageSize);

        //构建Search对象
        Search search =  new Search.Builder(searchSourceBuilder.toString())
                .addIndex(indexName)
                .addType(typeName)
                .build();

        SearchResult searchResult = null;
        try {
            searchResult = client.execute(search);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResult;
    }

}
