package com.jin.crawler.processor.mongo;

import com.jin.crawler.mongo.ReportMongo;
import com.jin.crawler.processor.HttpClientDownloader;
import com.jin.crawler.util.ConstantUtils;
import com.jin.crawler.util.CrawlerUtils;
import com.jin.crawler.util.DateUtils;
import com.jin.crawler.util.MongodbUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

public class SogouReportPageMongoSaveProcessor implements PageProcessor {



    private String projectId;
    private String projectName;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    private String keyWord;

    public SogouReportPageMongoSaveProcessor(){

    }


    public SogouReportPageMongoSaveProcessor(String projectId, String projectName, String keyWord){
        this.projectId = projectId;
        this.projectName = projectName;
        this.keyWord = keyWord;
    }
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");

    @Override
    public void process(Page page){
        try {
            Selectable selectable = page.getHtml().xpath("//div[@class=\"vrwrap\"]");
            List<Selectable> nodes = selectable.nodes();
            for(int i=0;i<nodes.size();i++) {
                Selectable node = nodes.get(i);
                String link = node.xpath("//h3[@class=\"vrTitle\"]//a/@href").get();
                if (StringUtils.isBlank(link)) {
                    continue;
                }
                System.out.println("link====="+link);
                /**
                 * link=====http://finance.eastmoney.com/a/20181204999193756.html
                 title=====速递：拟设立科创投资基金
                 content=====速递12月4日在上交所互动平台表示，鉴于公司控股股东上海蛟龙投资发展（集团）有限公司与嘉兴市政府在嘉兴共同建设全球性航空物流枢纽，结合嘉兴区...
                 source=====东方财富网 19小时前
                 media=====东方财富网
                 time=====19小时前
                 */
                String title = node.xpath("//h3[@class=\"vrTitle\"]//a//text()").get();
                if (StringUtils.isBlank(title)) {
                    continue;
                }
                System.out.println("title====="+title);
                String description = node.xpath("//div[@class=\"news-info\"]//p[@class=\"news-txt\"]/span/text()").get();
                if (StringUtils.isBlank(description)) {
                    continue;
                }
                System.out.println("description====="+description);
                String source = node.xpath("//div[@class=\"news-info\"]//p[@class=\"news-from\"]/text()").get();
                System.out.println("source====="+source);
                String[] array = source.trim().split(" ");
                String media = array[0];
                System.out.println("media====="+media);
                String time = array[1];
                time = CrawlerUtils.handleTime(time);
                /*if(time.endsWith("小时前")){
                    time = time.substring(0,time.indexOf("小时前"));
                    System.out.println("time1====="+time);
                    time = DateUtils.format(DateUtils.addTime(-Integer.parseInt(time)));
                }else {
                    time = time + " 00:00:00";
                }
                System.out.println("time2====="+time);*/
                /**
                 * 判断爬取的内容是否过旧,早于今天5天的都放弃
                 */
                if(StringUtils.isNotBlank(time) && !DateUtils.inNumDays(ConstantUtils.BEFORE_DAYS,time)){
                    continue;
                }
                System.out.println("======================================");
                ReportMongo report = new ReportMongo();
                report.setLink(link);
                report.setTitle(title);
                report.setContent(description);
                report.setDescription(description);
                report.setPageTime(time);
                report.setProjectId(projectId);
                report.setProjectName(projectName);
                report.setSourceMedia(media);
                report.setCreateTime(DateUtils.getCurrentUtilDate());
                report.setType(1);
                report.setKeyWord(keyWord);
                /**
                 * 插入前先判断是否已经爬取过
                 */
                List<ReportMongo> existList = (List<ReportMongo>) MongodbUtils.find(report,new String[]{"link"},new String[]{link});
                if(CollectionUtils.isEmpty(existList)){
                    MongodbUtils.save(report);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Site getSite() {
        return site;
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

    public static void main(String[] args) throws Exception {
//        Spider.create(new GithubRepoPageProcessor()).addUrl("https://github.com/code4craft").thread(5).run();
        String query = "圆通";
        String url = String.format("https://news.sogou.com/news?mode=1&manual=&query=%s&time=0&sort=1&page=1&w=03009900&dr=1&_asf=news.sogou.com&_ast=1544002432",query);
        Spider.create(new SogouReportPageMongoSaveProcessor()).setDownloader(new HttpClientDownloader()).addUrl(url).thread(1).run();
    }
}