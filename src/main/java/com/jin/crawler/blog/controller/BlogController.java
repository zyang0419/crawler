package com.jin.crawler.blog.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jin.crawler.blog.mapper.BlogMapper;
import com.jin.crawler.processor.HttpClientDownloader;
import com.jin.crawler.processor.SinaBlogPageProcessor;
import com.jin.crawler.processor.SogouReportPageProcessor;
import com.jin.crawler.processor.mongo.SinaBlogPageMongoSaveProcessor;
import com.jin.crawler.processor.mongo.SogouReportPageMongoSaveProcessor;
import com.jin.crawler.util.JsonResult;
import com.jin.crawler.util.PrivateConfig;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.jin.crawler.common.BaseController;
import org.springframework.web.client.RestTemplate;
import us.codecraft.webmagic.Spider;

import java.io.UnsupportedEncodingException;

/**
 * <p>
 * 博客爬取保存表 前端控制器
 * </p>
 *
 * @author jin
 * @since 2018-12-07
 */
@RestController
@RequestMapping("/blog")
public class BlogController extends BaseController {

    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation("爬取搜狗新闻数据")
    @GetMapping("/sina")
    public String sina(){
        try {
            JsonResult jr = restTemplate.getForObject(PrivateConfig.path,JsonResult.class);
            if(null != jr && null != jr.getData()){
                net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(JSON.toJSONString(jr));
                Object dataObject = jsonObject.get("data");
                if (null!=dataObject||""!=dataObject){
                    JSONArray jsonArray = JSONArray.parseArray(dataObject.toString());
                    if (!jsonArray.isEmpty()){
                        for(Object obj:jsonArray){
                            net.sf.json.JSONObject jsonO = net.sf.json.JSONObject.fromObject(obj);
                            String name = (String)jsonO.get("name");
                            String id = (String)jsonO.get("id");
                            Object jsoObject = jsonO.get("keyWords");
                            JSONArray jsonArray2 = JSONArray.parseArray(jsoObject.toString());
                            for (Object keyWord:jsonArray2) {
                                System.out.println(keyWord);
                                String url = String.format("http://search.sina.com.cn/?c=blog&q=%s&range=article&by=&sort=time&col=&source=&from=&country=&size=&time=&a=&page=1&dpc=1",java.net.URLEncoder.encode(keyWord.toString(),"gbk"));
                                Spider.create(new SinaBlogPageProcessor(blogMapper,id,name,keyWord.toString())).setDownloader(new HttpClientDownloader()).addUrl(url).thread(1).run();
                            }
                        }
                    }
                }
            }
            return "success";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "failure";
        }
    }

    @ApiOperation("爬取搜狗新闻数据存储到MongoDB数据库")
    @GetMapping("/sina_mongo")
    public String sina_mongo(){
        try {
            JsonResult jr = restTemplate.getForObject(PrivateConfig.path,JsonResult.class);
            if(null != jr && null != jr.getData()){
                net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(JSON.toJSONString(jr));
                Object dataObject = jsonObject.get("data");
                if (null!=dataObject||""!=dataObject){
                    JSONArray jsonArray = JSONArray.parseArray(dataObject.toString());
                    if (!jsonArray.isEmpty()){
                        for(Object obj:jsonArray){
                            net.sf.json.JSONObject jsonO = net.sf.json.JSONObject.fromObject(obj);
                            String name = (String)jsonO.get("name");
                            String id = (String)jsonO.get("id");
                            Object jsoObject = jsonO.get("keyWords");
                            JSONArray jsonArray2 = JSONArray.parseArray(jsoObject.toString());
                            for (Object keyWord:jsonArray2) {
                                System.out.println(keyWord);
                                String url = String.format("http://search.sina.com.cn/?c=blog&q=%s&range=article&by=&sort=time&col=&source=&from=&country=&size=&time=&a=&page=1&dpc=1",java.net.URLEncoder.encode(keyWord.toString(),"gbk"));
                                Spider.create(new SinaBlogPageMongoSaveProcessor(id,name,keyWord.toString())).setDownloader(new HttpClientDownloader()).addUrl(url).thread(1).run();
                            }
                        }
                    }
                }
            }
            return "success";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "failure";
        }
    }
}
