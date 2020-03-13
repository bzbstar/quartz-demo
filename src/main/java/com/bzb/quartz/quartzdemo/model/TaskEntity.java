package com.bzb.quartz.quartzdemo.model;

import lombok.Data;

/**
 * @ClassName TaskEntity
 * @Description
 * @Author baizhibin
 * @Date 2020/3/11 18:47
 * @Version 1.0
 */
@Data
public class TaskEntity<T> {

    private String jobGroup;
    private String jobName;
    private String startTime;
    private String endTime;
    private String cronExpression;
    private String jobDescription;
    private String jobClass; // 任务执行的类
    private T params;
}
