package com.jin.crawler.report.service.impl;

import com.jin.crawler.report.entity.Report;
import com.jin.crawler.report.mapper.ReportMapper;
import com.jin.crawler.report.service.IReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jin.crawler.util.ConstantUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 新闻爬取保存表 服务实现类
 * </p>
 *
 * @author jin
 * @since 2018-12-05
 */
@Service
@Slf4j
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements IReportService {

    @Autowired
    private ReportMapper reportMapper;

    @Override
    public boolean invalidReport(List<Report> reportList) {
        try {
            reportList.forEach(report -> {
                report.setDeleted(ConstantUtils.LOGIC_DELETED);
                reportMapper.updateById(report);
            });
            return  true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("批量逻辑删除report出现问题"+e.getMessage());
            return false;
        }
    }
}
