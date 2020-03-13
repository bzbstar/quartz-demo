package com.bzb.quartz.quartzdemo.model;

import lombok.Data;

@Data
public class AppQuartz {
    private Integer quartzId;

    private String jobName;

    private String jobGroup;

    private String startTime;

    private String cronExpression;

    private String invokeParams;

}