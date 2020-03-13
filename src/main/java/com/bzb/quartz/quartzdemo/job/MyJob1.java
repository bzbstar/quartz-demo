package com.bzb.quartz.quartzdemo.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @ClassName MyJob1
 * @Description 第一个定时任务
 * @Author baizhibin
 * @Date 2020/3/11 17:38
 * @Version 1.0
 */
@Slf4j
public class MyJob1 extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("定时任务：first");
    }
}
