package com.jin.crawler.postbar.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jin.crawler.postbar.entity.PostBar;
import com.jin.crawler.postbar.mapper.PostBarMapper;
import com.jin.crawler.postbar.service.IPostBarService;
import com.jin.crawler.util.ConstantUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 贴吧爬取保存表 服务实现类
 * </p>
 *
 * @author jin
 * @since 2018-12-05
 */
@Service
public class PostBarServiceImpl extends ServiceImpl<PostBarMapper, PostBar> implements IPostBarService {
    @Autowired
    private PostBarMapper postBarMapper;

    @Override
    public boolean invalidPostBar(List<PostBar> postBarList) {
        try {
            postBarList.forEach(postBar -> {
                postBar.setDeleted(ConstantUtils.LOGIC_DELETED);
                postBarMapper.updateById(postBar);
            });
            return  true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
