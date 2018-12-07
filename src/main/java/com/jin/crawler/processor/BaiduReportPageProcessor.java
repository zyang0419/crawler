package com.jin.crawler.processor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jin.crawler.report.entity.Report;
import com.jin.crawler.report.mapper.ReportMapper;
import com.jin.crawler.util.ConstantUtils;
import com.jin.crawler.util.CrawlerUtils;
import com.jin.crawler.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

public class BaiduReportPageProcessor implements PageProcessor {



    private ReportMapper reportMapper;
    private String projectId;
    private String projectName;
    private String keyWord;

    public BaiduReportPageProcessor(){

    }

    public BaiduReportPageProcessor(ReportMapper reportMapper){
        this.reportMapper = reportMapper;
    }

    public BaiduReportPageProcessor(ReportMapper reportMapper, String projectId, String projectName,String keyWord){
        this.reportMapper = reportMapper;
        this.projectId = projectId;
        this.projectName = projectName;
        this.keyWord = keyWord;
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
            Selectable selectable = page.getHtml().xpath("//div[@class=\"result\"]");
            List<Selectable> nodes = selectable.nodes();
            for(int i=0;i<nodes.size();i++) {
                Selectable node = nodes.get(i);
                String link = node.xpath("//h3[@class=\"c-title\"]//a/@href").get();
                if (StringUtils.isBlank(link)) {
                    continue;
                }
                System.out.println("link====="+link);
                /**
                 * link=====http://stock.eastmoney.com/a/20181123991511350.html
                 title===== 11月23日加速下跌
                 description=====  以下是在北京时间11月23日10:41分盘口异动快照:   11月23日,盘中加速下跌,5分钟内跌幅超过2%,截至10点41分,报4.02元,成交542.43万元,...
                 source===== 东方财富网   2018年11月23日 10:41
                 media=====东方财富网  
                 time_年月日=====2018-11-23 10:41
                 */
                String title = node.xpath("//h3[@class=\"c-title\"]//a//text()").get();
                if (StringUtils.isBlank(title)) {
                    continue;
                }
                System.out.println("title====="+title);
                String description = node.xpath("//div[@class=\"c-summary\"]/text()").get();
                if (StringUtils.isBlank(description)) {
                    continue;
                }
                System.out.println("description====="+description);
                String source = node.xpath("//div[@class=\"c-summary\"]//p[@class=\"c-author\"]/text()").get();
                System.out.println("source====="+source);
                String[] array = source.trim().split("\\s+");
                String media = array[0];
                System.out.println("media====="+media);
                String time = array[1];
                if(time.indexOf("年") != -1){
                    time = array[1] +" "+array[2];
                }
                time = CrawlerUtils.handleTime(time);

                /**
                 * 判断爬取的内容是否过旧,早于今天5天的都放弃
                 */
                if(StringUtils.isNotBlank(time) && !DateUtils.inNumDays(ConstantUtils.BEFORE_DAYS,time)){
                    continue;
                }
                System.out.println("======================================");
                Report report = new Report();
                report.setLink(link);
                report.setTitle(title);
                report.setContent(description);
                report.setDescription(description);
                report.setPageTime(time);
                report.setProjectId(projectId);
                report.setProjectName(projectName);
                report.setSourceMedia(media);
                report.setCreateTime(DateUtils.getCurrentUtilDate());
                report.setKeyWord(keyWord);
                report.setType(2);
             /**
                 * 插入前先判断是否已经爬取过
                 */
                List<Report> existList = reportMapper.selectList(
                        new QueryWrapper<Report>()
                                .eq("link",link)
                );
                if(CollectionUtils.isEmpty(existList)){
                    reportMapper.insert(report);
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


    public ReportMapper getReportMapper() {
        return reportMapper;
    }

    public void setReportMapper(ReportMapper reportMapper) {
        this.reportMapper = reportMapper;
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
        String url = String.format("https://www.baidu.com/s?tn=news&rtt=4&bsst=1&cl=2&wd=%s&x_bfe_rqs=03E80&x_bfe_tjscore=0.060224&tngroupname=organic_news&pn=0",query);
        Spider.create(new BaiduReportPageProcessor()).setDownloader(new HttpClientDownloader()).addUrl(url).thread(1).run();
    }
}