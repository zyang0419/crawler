package com.jin.crawler.postbar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jin.crawler.postbar.entity.PostBar;

import java.util.List;

/**
 * <p>
 * 贴吧爬取保存表 服务类
 * </p>
 *
 * @author jin
 * @since 2018-12-05
 */
public interface IPostBarService extends IService<PostBar> {
    /**
     *
     *批量逻辑删除
     *@author Xingheng.Zhang
     *@date 2018-12-6 14:39
     *@param
     *@return
     */
    public boolean invalidPostBar(List<PostBar> postBarList);

}
