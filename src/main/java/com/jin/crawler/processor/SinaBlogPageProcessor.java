package com.jin.crawler.processor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jin.crawler.blog.entity.Blog;
import com.jin.crawler.blog.mapper.BlogMapper;
import com.jin.crawler.util.ConstantUtils;
import com.jin.crawler.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;



/**
 *
 *新浪博客搜索引擎爬取
 *@author Xingheng.Zhang
 *@date 2018-12-7 15:12
 *@param
 *@return
 */
public class SinaBlogPageProcessor implements PageProcessor {



    private BlogMapper blogMapper;
    private String projectId;
    private String projectName;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    private String keyWord;

    public SinaBlogPageProcessor(){

    }

    public SinaBlogPageProcessor(BlogMapper blogMapper){
        this.blogMapper = blogMapper;
    }

    public SinaBlogPageProcessor(BlogMapper blogMapper, String projectId, String projectName, String keyWord){
        this.blogMapper = blogMapper;
        this.projectId = projectId;
        this.projectName = projectName;
        this.keyWord = keyWord;
    }
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");

    @Override
    public void process(Page page){
        try {
            Selectable selectable = page.getHtml().xpath("//div[contains(@class,\"box-result\")]");
            List<Selectable> nodes = selectable.nodes();
            for(int i=0;i<nodes.size();i++) {
                Selectable node = nodes.get(i);
                String link = node.xpath("//h2[@class=\"r-info-blog-tit\"]//a/@href").get();
                if (StringUtils.isBlank(link)) {
                    continue;
                }
                System.out.println("link====="+link);

                String title = node.xpath("//h2[@class=\"r-info-blog-tit\"]//a//text()").get();
                if (StringUtils.isBlank(title)) {
                    continue;
                }
                System.out.println("title====="+title);
                String description = node.xpath("//div[@class=\"clearfix\"]//p[@class=\"content\"]/text()").get();
                if (StringUtils.isBlank(description)) {
                    continue;
                }
                System.out.println("description====="+description);
                String source = node.xpath("//div[@class=\"clearfix\"]//a[@class=\"rib-author\"]/text()").get();
                System.out.println("source====="+source);

                String time = node.xpath("//div[@class=\"clearfix\"]//span[@class=\"fgray_time\"]/text()").get();
                System.out.println("time====="+time);

                /**
                 * 判断爬取的内容是否过旧,早于今天5天的都放弃
                 */
                if(StringUtils.isBlank(time) || !DateUtils.inNumDays(ConstantUtils.BEFORE_DAYS,time)){
                    continue;
                }
                System.out.println("======================================");
                Blog blog = new Blog();
                blog.setLink(link);
                blog.setTitle(title);
                blog.setContent(description);
                blog.setDescription(description);
                blog.setPageTime(time);
                blog.setProjectId(projectId);
                blog.setProjectName(projectName);
                blog.setSourceName(source);
                blog.setCreateTime(DateUtils.getCurrentUtilDate());
                blog.setType(1);
                blog.setKeyWord(keyWord);
                /**
                 * 插入前先判断是否已经爬取过
                 */
                List<Blog> existList = blogMapper.selectList(
                        new QueryWrapper<Blog>()
                                .eq("link",link)
                );
                if(CollectionUtils.isEmpty(existList)){
                    blogMapper.insert(blog);
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


    public BlogMapper getBlogMapper() {
        return blogMapper;
    }

    public void setBlogMapper(BlogMapper reportMapper) {
        this.blogMapper = blogMapper;
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
        String query = "圆通快递";
        String url = String.format("http://search.sina.com.cn/?c=blog&q=%s&range=article&by=&sort=time&col=&source=&from=&country=&size=&time=&a=&page=1&dpc=1",java.net.URLEncoder.encode(query,"gbk"));
        System.out.println("url=============================="+url);
        Spider.create(new SinaBlogPageProcessor()).setDownloader(new HttpClientDownloader()).addUrl(url).thread(1).run();
    }
}