package com.dg.mall.gateway.core.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author hn
 * @Description: 封装日志消息实体类
 * @Date 2019/8/2 10:25
 * @Version V1.0
 **/

@Data
public class SysOperationLogDTO implements Serializable{

    private static final long serialVersionUID=1L;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * ip地址
     */
    private String ipAddress;

    /**
     * 请求地址
     */
    private String requestUrl;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 请求类型
     */
    private String requestType;

    /**
     * 操作记录
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    public SysOperationLogDTO(String userName, String ipAddress, String requestUrl, String requestType) {
        this.userName = userName;
        this.ipAddress = ipAddress;
        this.requestUrl = requestUrl;
        this.requestType = requestType;
        this.createTime = new Date();
        this.createUser = userName;
    }
}
