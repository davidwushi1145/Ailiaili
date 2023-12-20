package com.bilibili.service.impl;

import com.bilibili.dao.domain.TAdPerformance;
import com.bilibili.dao.domain.UserInfo;
import com.bilibili.dao.domain.Video;
import com.bilibili.dao.repository.AdPerformanceRepository;
import com.bilibili.dao.repository.UserInfoRepository;
import com.bilibili.dao.repository.VideoRepository;
import com.bilibili.service.ElasticSearchService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private AdPerformanceRepository adPerformanceRepository;

    private final RestHighLevelClient client;

    @Autowired
    public ElasticSearchServiceImpl(RestHighLevelClient client) {
        this.client = client;
    }


    @Override
    public void addVideo(Video video){
        videoRepository.save(video);
    }

    @Override
    public Video getVideo(String keyword){
        return videoRepository.findByTitleLike(keyword);
    }

    public void updateVideo(Video video) {
        // 构建一个更新请求
        UpdateRequest updateRequest = new UpdateRequest("videos", String.valueOf(video.getId()));
        Map<String, Object> jsonMap = new HashMap<>();

        jsonMap.put("likes", video.getLikes());
        jsonMap.put("collections", video.getCollections());
        jsonMap.put("coins", video.getCoins());
        jsonMap.put("views", video.getViews());

        updateRequest.doc(jsonMap);

        try {
            // 执行更新操作
            UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
            System.out.println(updateResponse.toString());
            // 根据需要处理响应
        } catch (IOException e) {
            // 处理异常，记录日志
        }
    }


    @Override
    public void deleteAllVideos(){
        videoRepository.deleteAll();
    }

    @Override
    public void addUserInfo(UserInfo userInfo){
        userInfoRepository.save(userInfo);
    }

    @Override
    public void deleteAllUserInfos(){
        userInfoRepository.deleteAll();
    }

    @Override
    public void updateUserInfo(UserInfo userInfo) {
        // 构建一个更新请求
        UpdateRequest updateRequest = new UpdateRequest("userinfos", String.valueOf(userInfo.getUserId()));
        Map<String, Object> jsonMap = new HashMap<>();

        updateRequest.doc(jsonMap);

        try {
            // 执行更新操作
            UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
            System.out.println(updateResponse.toString());
            // 根据需要处理响应
        } catch (IOException e) {
            // 处理异常，记录日志
        }
    }

    @Override
    public void addAdPerformance(TAdPerformance adPerformance) {
        adPerformanceRepository.save(adPerformance);
    }

    @Override
    public void deleteAllAdPerformances() {
        adPerformanceRepository.deleteAll();
    }

    @Override
    public void updateAdPerformance(TAdPerformance adPerformance) {
        // 构建一个更新请求
        UpdateRequest updateRequest = new UpdateRequest("tadperformance", String.valueOf(adPerformance.getAdId()));
        Map<String, Object> jsonMap = new HashMap<>();

        jsonMap.put("clicks", adPerformance.getClicks());
        jsonMap.put("impressions", adPerformance.getImpressions());
        jsonMap.put("conversions", adPerformance.getConversions());

        updateRequest.doc(jsonMap);

        try {
            // 执行更新操作
            UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
            System.out.println(updateResponse.toString());
            // 根据需要处理响应
        } catch (IOException e) {
            // 处理异常，记录日志
        }
    }

    @Override
    public TAdPerformance getAdPerformance(String keyword) {
        return adPerformanceRepository.findByAdId(Long.parseLong(keyword));
    }


    @Override
    public List<Map<String,Object>> getContents(String keyword, Integer pageNumber, Integer pageSize) throws IOException {
        String[] indexes = {"videos","userinfos","tadperformance"};
        SearchRequest searchRequest = new SearchRequest(indexes);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(pageSize);
        searchSourceBuilder.from(pageNumber-1);
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyword,"title","nick","description");
        searchSourceBuilder.query(multiMatchQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        searchSourceBuilder.timeout(new TimeValue(60,TimeUnit.SECONDS));
        //高亮显示
        String[] array = {"title","nick","description"};
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        for(String key:array){
            highlightBuilder.fields().add(new HighlightBuilder.Field(key));
        }
        //多个字段高亮
        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.preTags("<span style=\"color:red\">");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);
        //执行搜索
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        List<Map<String,Object>> arrayList = new ArrayList<>();
        for(SearchHit hit :searchResponse.getHits()){
            //处理高亮字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            Map<String,Object> sourceMap = hit.getSourceAsMap();
            for(String key :array){
                HighlightField highlightField = highlightFields.get(key);
                if(highlightField !=null){
                    Text[] fragments = highlightField.getFragments();
                    String str = Arrays.toString(fragments);
                    str = str.substring(1,str.length()-1);
                    sourceMap.put(key,str);
                }
            }
            arrayList.add(sourceMap);
        }
        return arrayList;
    }
}
