package com.jin.crawler.news.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jin.crawler.blog.entity.Blog;
import com.jin.crawler.blog.mapper.BlogMapper;
import com.jin.crawler.blog.service.impl.BlogServiceImpl;
import com.jin.crawler.news.mapper.NewsMapper;
import com.jin.crawler.news.service.impl.NewsServiceImpl;
import com.jin.crawler.postbar.entity.PostBar;
import com.jin.crawler.postbar.mapper.PostBarMapper;
import com.jin.crawler.postbar.service.impl.PostBarServiceImpl;
import com.jin.crawler.report.entity.Report;
import com.jin.crawler.report.mapper.ReportMapper;
import com.jin.crawler.report.service.impl.ReportServiceImpl;
import com.jin.crawler.util.DateUtils;
import com.jin.crawler.util.JsonResult;
import com.jin.crawler.util.ResultCode;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.jin.crawler.common.BaseController;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jin
 * @since 2018-12-06
 */
@RestController
@RequestMapping("/news")
public class NewsController extends BaseController {

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private NewsServiceImpl newsService;

    @Autowired
    private ReportServiceImpl reportService;

    @Autowired
    private PostBarServiceImpl postBarService;

    @Autowired
    private BlogServiceImpl blogService;

    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private PostBarMapper postBarMapper;

    @Autowired
    private BlogMapper blogMapper;

    /**
     *
     *爬虫数据迁移到monitor数据库
     *@author Xingheng.Zhang
     *@date 2018-12-6 11:06
     *@param
     *@return
     */
    @ApiOperation("爬虫数据迁移到monitor数据库")
    @GetMapping("/transfer")
    public String transfer(){
        try {
            Date startTime = DateUtils.dayLastHour();
            Date endTime = DateUtils.getCurrentUtilDate();
            /**
             * 获取上一小时内的有效数据
             */

            /**
             * 迁移爬取的新闻数据到监控数据库
             */
            List<Report> reportList = reportMapper.selectList(new QueryWrapper<Report>()
                                                .isNotNull("title")
                                                .isNotNull("content")
                                                .eq("deleted","0")
                                                .between("create_time",startTime,endTime)
            );
            boolean result = newsService.transferReport(reportList);
            System.out.println("*********transfer report result ========" + result);
            boolean invalidReportResult = reportService.invalidReport(reportList);

            /**
             * 迁移爬取的贴吧数据到监控数据库
             */
            List<PostBar> postBarList = postBarMapper.selectList(new QueryWrapper<PostBar>()
                    .isNotNull("title")
                    .isNotNull("content")
                    .eq("deleted","0")
                    .between("create_time",startTime,endTime)
            );
            boolean returnValue =  newsService.transferPostBar(postBarList);
            System.out.println("*********transfer report result ========" + returnValue);
            boolean invalidPostBarResult = postBarService.invalidPostBar(postBarList);

            /**
             * 迁移爬取的新浪博客数据到监控数据库
             */
            List<Blog> blogList = blogMapper.selectList(new QueryWrapper<Blog>()
                    .isNotNull("title")
                    .isNotNull("content")
                    .eq("deleted","0")
                    .between("create_time",startTime,endTime)
            );
            boolean returnBlogValue =  newsService.transferBlog(blogList);
            System.out.println("*********transfer blog result ========" + returnBlogValue);
            boolean invalidBlogResult = blogService.invalidBlog(blogList);

        } catch (Exception e) {
            e.printStackTrace();
            return "failure";

        }
        return "success";
    }

}
