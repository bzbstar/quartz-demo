package com.bzb.quartz.quartzdemo.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @ClassName MyJob2
 * @Description 第二个定时任务
 * @Author baizhibin
 * @Date 2020/3/11 17:42
 * @Version 1.0
 */
@Slf4j
public class MyJob2 extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("定时任务：second");
    }
}
