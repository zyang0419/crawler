package com.jin.crawler.postbar.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jin.crawler.common.BaseController;
import com.jin.crawler.postbar.mapper.PostBarMapper;
import com.jin.crawler.postbar.service.impl.PostBarServiceImpl;
import com.jin.crawler.processor.mongo.BaiduTiebaPageMongoSaveProcessor;
import com.jin.crawler.processor.BaiduTiebaPageProcessor;
import com.jin.crawler.processor.HttpClientDownloader;
import com.jin.crawler.util.JsonResult;
import com.jin.crawler.util.PrivateConfig;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import us.codecraft.webmagic.Spider;

/**
 * <p>
 * 贴吧爬取保存表 前端控制器
 * </p>
 *
 * @author jin
 * @since 2018-12-05
 */
@RestController
@RequestMapping("/postbar")
public class PostBarController extends BaseController {

    @Autowired
    private PostBarMapper postBarMapper;

    @Autowired
    private PostBarServiceImpl postBarService;

    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation("爬取百度贴吧数据")
    @GetMapping("/baidu")
    public String baidu(){
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
//                        System.out.println("================================="+ jsonArray2);
                        for (Object keyWord:jsonArray2) {
                            System.out.println(keyWord);
                            String url = String.format("http://tieba.baidu.com/f/search/res?isnew=1&kw=&qw=%s&rn=20&un=&only_thread=0&sm=1&sd=&ed=&pn=1", keyWord);
                            Spider.create(new BaiduTiebaPageProcessor(postBarMapper,id,name,keyWord.toString())).setDownloader(new HttpClientDownloader()).addUrl(url).thread(1).run();
                        }
                    }
                }
            }
        }
        return "success";
    }

    @ApiOperation("爬取百度贴吧数据保存到MongoDB数据库")
    @GetMapping("/baidu_mongo")
    public String baidu_mongo(){
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
//                        System.out.println("================================="+ jsonArray2);
                        for (Object keyWord:jsonArray2) {
                            System.out.println(keyWord);
                            String url = String.format("http://tieba.baidu.com/f/search/res?isnew=1&kw=&qw=%s&rn=20&un=&only_thread=0&sm=1&sd=&ed=&pn=1", keyWord);
                            Spider.create(new BaiduTiebaPageMongoSaveProcessor(id,name,keyWord.toString())).setDownloader(new HttpClientDownloader()).addUrl(url).thread(1).run();
                        }
                    }
                }
            }
        }
        return "success";
    }

}
