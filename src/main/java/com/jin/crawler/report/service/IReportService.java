package com.jin.crawler.report.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jin.crawler.report.entity.Report;

import java.util.List;

/**
 * <p>
 * 新闻爬取保存表 服务类
 * </p>
 *
 * @author jin
 * @since 2018-12-05
 */
public interface IReportService extends IService<Report> {
    /**
     *
     *批量逻辑删除
     *@author Xingheng.Zhang
     *@date 2018-12-6 14:39
     *@param
     *@return
     */
    public boolean invalidReport(List<Report> reportList);
}
