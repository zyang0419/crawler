package com.jin.crawler.common;

import lombok.Data;

/**
 * mybatis-plus 模板生成基础javabean
 * @author Xingheng.Zhang
 * @date Created in 2018-12-3 14:21
 */
@Data
public class BaseEntity {

    /**
     * 逻辑删除标识符1为逻辑删除，默认值为0
     */
    private Integer deleted;
}
