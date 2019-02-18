package com.jin.crawler.report.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jin.crawler.processor.BaiduReportPageProcessor;
import com.jin.crawler.processor.BaiduTiebaPageProcessor;
import com.jin.crawler.processor.HttpClientDownloader;
import com.jin.crawler.processor.SogouReportPageProcessor;
import com.jin.crawler.processor.mongo.BaiduReportPageMongoSaveProcessor;
import com.jin.crawler.processor.mongo.SogouReportPageMongoSaveProcessor;
import com.jin.crawler.report.mapper.ReportMapper;
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

/**
 * <p>
 * 新闻爬取保存表 前端控制器
 * </p>
 *
 * @author jin
 * @since 2018-12-05
 */
@RestController
@RequestMapping("/report")
public class ReportController extends BaseController {

    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    private RestTemplate restTemplate;


    @ApiOperation("爬取搜狗新闻数据")
    @GetMapping("/sogou")
    public String sogou(){
//        String apiUrl = "http://192.168.2.19:8080/api/task/projectWords?key=QiChuan_MONITOR_PUBLIC_KEY&appId=QiChuan_MONITOR_APP_ID";
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
                            String sougout_url = String.format("https://news.sogou.com/news?mode=1&manual=&query=%s&time=0&sort=1&page=1&w=03009900&dr=1&_asf=news.sogou.com&_ast=1544002432",keyWord);
                            Spider.create(new SogouReportPageProcessor(reportMapper,id,name,keyWord.toString())).setDownloader(new HttpClientDownloader()).addUrl(sougout_url).thread(1).run();
                        }
                    }
                }
            }
        }
        return "success";
    }

    @ApiOperation("爬取搜狗新闻数据存储到MongoDB数据库")
    @GetMapping("/sogou_mongo")
    public String sogou_mongo(){
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
                            String sougout_url = String.format("https://news.sogou.com/news?mode=1&manual=&query=%s&time=0&sort=1&page=1&w=03009900&dr=1&_asf=news.sogou.com&_ast=1544002432",keyWord);
                            Spider.create(new SogouReportPageMongoSaveProcessor(id,name,keyWord.toString())).setDownloader(new HttpClientDownloader()).addUrl(sougout_url).thread(1).run();
                        }
                    }
                }
            }
        }
        return "success";
    }

    @ApiOperation("爬取百度新闻数据")
    @GetMapping("/baidu")
    public String baidu(){
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
                            String baidu_url = String.format("https://www.baidu.com/s?tn=news&rtt=4&bsst=1&cl=2&wd=%s&x_bfe_rqs=03E80&x_bfe_tjscore=0.060224&tngroupname=organic_news&pn=0",keyWord);
                            Spider.create(new BaiduReportPageProcessor(reportMapper,id,name,keyWord.toString())).setDownloader(new HttpClientDownloader()).addUrl(baidu_url).thread(1).run();
                        }
                    }
                }
            }
        }
        return "success";
    }

    @ApiOperation("爬取百度新闻数据存储到MongoDB数据库")
    @GetMapping("/baidu_mongo")
    public String baidu_mongo(){
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
                            String baidu_url = String.format("https://www.baidu.com/s?tn=news&rtt=4&bsst=1&cl=2&wd=%s&x_bfe_rqs=03E80&x_bfe_tjscore=0.060224&tngroupname=organic_news&pn=0",keyWord);
                            Spider.create(new BaiduReportPageMongoSaveProcessor(id,name,keyWord.toString())).setDownloader(new HttpClientDownloader()).addUrl(baidu_url).thread(1).run();
                        }
                    }
                }
            }
        }
        return "success";
    }
}
