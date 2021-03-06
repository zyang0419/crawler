package com.jin.crawler.processor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jin.crawler.postbar.entity.PostBar;
import com.jin.crawler.postbar.mapper.PostBarMapper;
import com.jin.crawler.util.ConstantUtils;
import com.jin.crawler.util.CrawlerUtils;
import com.jin.crawler.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;
/**
 *
 *百度贴吧搜索引擎爬取
 *@author Xingheng.Zhang
 *@date 2018-12-7 15:12
 *@param
 *@return
 */
public class BaiduTiebaPageProcessor implements PageProcessor {


    private PostBarMapper postBarMapper;
    private String projectId;
    private String projectName;
    private String keyWord;

    public BaiduTiebaPageProcessor(){

    }

    public BaiduTiebaPageProcessor(PostBarMapper postBarMapper){
        this.postBarMapper = postBarMapper;
    }

    public BaiduTiebaPageProcessor(PostBarMapper postBarMapper,String projectId, String projectName,String keyWord){
        this.postBarMapper = postBarMapper;
        this.projectId = projectId;
        this.projectName = projectName;
        this.keyWord = keyWord;
    }
    public PostBarMapper getPostBarMapper() {
        return postBarMapper;
    }

    public void setPostBarMapper(PostBarMapper postBarMapper) {
        this.postBarMapper = postBarMapper;
    }
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");


    @Override
    public void process(Page page){
        try {
            Selectable selectable = page.getHtml().xpath("//div[@class=\"s_post_list\"]//div[@class=\"s_post\"]");
            List<Selectable> nodes = selectable.nodes();
            String BASE_URL = "https://tieba.baidu.com";
//            List<PostBar> postBarList = new ArrayList<>();
            int len = nodes.size();
            for(int i=0;i<len;i++) {
                //过滤掉第一条主题贴吧
                if (i == 0) {
                    continue;
                }
                Selectable node = nodes.get(i);
                String link = node.xpath("//span[@class=\"p_title\"]//a[@class=\"bluelink\"]/@href").get();
                if (StringUtils.isBlank(link)) {
                    continue;
                }
                System.out.println("link====="+BASE_URL+link);
                //防止重复爬取
                String pid = CrawlerUtils.getParam(link,"pid");
                System.out.println("pid====="+pid);
                String title = node.xpath("//span[@class=\"p_title\"]//a[@class=\"bluelink\"]/text()").get();
                if (StringUtils.isBlank(title)) {
                    continue;
                }
                System.out.println("title====="+title);
                String content = node.xpath("//div[@class=\"p_content\"]/text()").get();
                if (StringUtils.isBlank(content)) {
                    continue;
                }
                System.out.println("content====="+content);
                String tiebaName = node.xpath("//a[@class=\"p_forum\"]//font[@class=\"p_violet\"]/text()").get();
                System.out.println("tiebaName====="+tiebaName);
                String time = node.xpath("font[contains(@class,\"p_date\")]/text()").get();
                System.out.println("time====="+time);
                /**
                 * 判断爬取的内容是否过旧,早于今天5天的都放弃
                 */
                if(StringUtils.isBlank(time) || !DateUtils.inNumDays(ConstantUtils.BEFORE_DAYS,time)){
                    continue;
                }
                System.out.println("======================================");
                PostBar postBar = new PostBar();
                postBar.setTitle(title);
                postBar.setContent(content);
                postBar.setLink(BASE_URL+link);
                postBar.setPostBarName(tiebaName);
                postBar.setPageTime(time);
                postBar.setType(1);
                postBar.setCreateTime(DateUtils.getCurrentUtilDate());
                postBar.setUuid(pid);
                postBar.setKeyWord(keyWord);
                postBar.setProjectId(projectId);
                postBar.setProjectName(projectName);
//                postBarList.add(postBar);
                /**
                 * 插入前先判断是否已经爬取过
                 */
                List<PostBar> existList = postBarMapper.selectList(
                        new QueryWrapper<PostBar>()
                        .eq("uuid",pid)
                );
                if(CollectionUtils.isEmpty(existList)){
                    postBarMapper.insert(postBar);
                }
            }
//            System.out.println(postBarList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Site getSite() {
        return site;
    }



    public static void main(String[] args) throws Exception {
//          Spider.create(new BaiduTiebaPageProcessor()).setDownloader(new HttpClientDownloader()).addUrl("http://tieba.baidu.com/f/search/res?isnew=1&kw=&qw=%E5%9C%86%E9%80%9A%E5%BF%AB%E9%80%92&rn=20&un=&only_thread=0&sm=1&sd=&ed=&pn=1").thread(1).run();
        System.out.println(DateUtils.parse("2018-12-05 14:26",DateUtils.FORMAT_TIME_MINUTE));
    }
}