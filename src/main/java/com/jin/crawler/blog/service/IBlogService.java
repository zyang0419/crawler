package com.jin.crawler.blog.service;

import com.jin.crawler.blog.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 博客爬取保存表 服务类
 * </p>
 *
 * @author jin
 * @since 2018-12-07
 */
public interface IBlogService extends IService<Blog> {
    /**
     *
     *批量逻辑删除
     *@author Xingheng.Zhang
     *@date 2018-12-7 16:06
     *@param
     *@return
     */
    public boolean invalidBlog(List<Blog> blogList);
}
