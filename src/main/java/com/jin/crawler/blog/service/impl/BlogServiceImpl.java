package com.jin.crawler.blog.service.impl;

import com.jin.crawler.blog.entity.Blog;
import com.jin.crawler.blog.mapper.BlogMapper;
import com.jin.crawler.blog.service.IBlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jin.crawler.postbar.entity.PostBar;
import com.jin.crawler.util.ConstantUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 博客爬取保存表 服务实现类
 * </p>
 *
 * @author jin
 * @since 2018-12-07
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public boolean invalidBlog(List<Blog> blogList) {
        try {
            blogList.forEach(blog -> {
                blog.setDeleted(ConstantUtils.LOGIC_DELETED);
                blogMapper.updateById(blog);
            });
            return  true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
