package com.jin.crawler.news.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.jin.crawler.blog.entity.Blog;
import com.jin.crawler.news.entity.News;
import com.jin.crawler.news.mapper.NewsMapper;
import com.jin.crawler.news.service.INewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jin.crawler.postbar.entity.PostBar;
import com.jin.crawler.postbar.mapper.PostBarMapper;
import com.jin.crawler.report.entity.Report;
import com.jin.crawler.report.mapper.ReportMapper;
import com.jin.crawler.util.ConstantEnum;
import com.jin.crawler.util.ConstantUtils;
import com.jin.crawler.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jin
 * @since 2018-12-06
 */
@Service
@DS("monitor")
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements INewsService {

    @Autowired
    private NewsMapper newsMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean transferPostBar(List<PostBar> postBarList) {
        try {
            postBarList.forEach(postBar -> {
                News news = new News();
                news.setContent(postBar.getContent());
                news.setTitle(postBar.getTitle());
                news.setUrl(postBar.getLink());
                news.setProjectId(postBar.getProjectId());
                news.setPlatform(ConstantEnum.POSTBAR.getIndex());
                news.setCreateTm(DateUtils.getCurrentUtilDate());
                news.setPublishTm(postBar.getPageTime());
                news.setSemantic(ConstantUtils.NEUTRAL);
                news.setStatus(ConstantUtils.STATUS_VALID);
                news.setDescription(postBar.getContent());
                news.setSimilarNumber(ConstantUtils.ZERO);
                news.setSource(postBar.getType() == ConstantUtils.ONE ? "百度贴吧":"其它贴吧");
                newsMapper.insert(news);

            });

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean transferReport(List<Report> reportList) {
        try {
            reportList.forEach(report -> {
                News news = new News();
                news.setContent(report.getContent());
                news.setTitle(report.getTitle());
                news.setUrl(report.getLink());
                news.setProjectId(report.getProjectId());
                news.setPlatform(ConstantEnum.NEWS.getIndex());
                news.setCreateTm(DateUtils.getCurrentUtilDate());
                news.setPublishTm(report.getPageTime());
                news.setSemantic(ConstantUtils.NEUTRAL);
                news.setStatus(ConstantUtils.STATUS_VALID);
                news.setSimilarNumber(ConstantUtils.ZERO);
                news.setDescription(report.getDescription());
                news.setSource(report.getSourceMedia());
                newsMapper.insert(news);

            });

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean transferBlog(List<Blog> blogList) {
        try {
            blogList.forEach(blog -> {
                News news = new News();
                news.setContent(blog.getContent());
                news.setTitle(blog.getTitle());
                news.setUrl(blog.getLink());
                news.setProjectId(blog.getProjectId());
                news.setPlatform(ConstantEnum.BLOG.getIndex());
                news.setCreateTm(DateUtils.getCurrentUtilDate());
                news.setPublishTm(blog.getPageTime());
                news.setSemantic(ConstantUtils.NEUTRAL);
                news.setStatus(ConstantUtils.STATUS_VALID);
                news.setSimilarNumber(ConstantUtils.ZERO);
                news.setDescription(blog.getDescription());
                news.setSource("新浪博客");
                newsMapper.insert(news);

            });

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

