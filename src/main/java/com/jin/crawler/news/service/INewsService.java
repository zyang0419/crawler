package com.jin.crawler.news.service;

import com.jin.crawler.news.entity.News;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jin.crawler.postbar.entity.PostBar;
import com.jin.crawler.report.entity.Report;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jin
 * @since 2018-12-06
 */
public interface INewsService extends IService<News> {

    /**
     *
     * 迁移爬取的数据到监控数据库
     *@author Xingheng.Zhang
     *@date 2018-12-6 9:36
     *@param
     *@return
     */
   public boolean transferPostBar(List<PostBar> postBarList);
    /**
     *
     * 迁移爬取的数据到监控数据库
     *@author Xingheng.Zhang
     *@date 2018-12-6 9:36
     *@param
     *@return
     */
   public boolean transferReport(List<Report> reportList);

}
