package com.jin.crawler.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class JsonResult implements Serializable {
    /**     * 	 */
    private static final long serialVersionUID = 1L;
    private static final int SUCCESS = 0;
    private static final int ERROR = 1;
    private static final String MESSAGE = "成功";
    private String code;
    private String msg;
    private Object data;
    private Object object;
    private Object data3;
    private Object data4;
    private int pageNum;
    private int pageSize;
    private long count;
    private String type;
    private String key;
    private String url;

    public JsonResult() {

    }

//    public JsonResult() {
//        this.setCode(ResultCode.SUCCESS.val());
//        this.setMessage(ResultCode.SUCCESS.msg());
//    }

    public JsonResult(ResultCode code) {
        this.setCode(code.val());
        this.setMsg(code.msg());
    }

    public JsonResult(ResultCode code, String msg) {
        this.setCode(code.val());
        this.setMsg(msg);
    }

    public JsonResult(ResultCode code, String msg, Object data) {
        this.setCode(code.val());
        this.setMsg(msg);
        this.setData(data);

        //根据不同的结果 做处理
        /*if(data instanceof EntityBeanSet){
            //添加分页
            EntityBeanSet ebs = (EntityBeanSet)data;
            this.setPageCount(ebs.getPageCount());
            this.setPageNum(ebs.getPageNum());
            this.setData(ebs.getResult());
        }else{
            this.setData(data);
        }*/

    }


}