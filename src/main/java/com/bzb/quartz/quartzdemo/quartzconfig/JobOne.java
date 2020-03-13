package com.bzb.quartz.quartzdemo.quartzconfig;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
@Slf4j
public class JobOne implements Job{

    @Value("${server.port}")
    private String port;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap data=context.getTrigger().getJobDataMap();
        String params =(String) data.get("params");
            //在这里实现业务逻辑
        log.info("我是jobOne， 参数={}, 我的端口是：{}", params, port);
    }
}