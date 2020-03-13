package com.bzb.quartz.quartzdemo.config;

import com.bzb.quartz.quartzdemo.job.MyJob1;
import com.bzb.quartz.quartzdemo.job.MyJob2;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfiguration {

    // 使用jobDetail包装job
    @Bean
    public JobDetail myJobDetail() {
        return JobBuilder.newJob(MyJob1.class).withIdentity("myJob1").storeDurably().build();
    }

    // 把jobDetail注册到trigger上去
    @Bean
    public Trigger myJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(15).repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(myJobDetail())
                .withIdentity("myJob1Trigger")
                .withSchedule(scheduleBuilder)
                .build();
    }

    // 使用jobDetail包装job
    @Bean
    public JobDetail myCronJobDetail() {
        return JobBuilder.newJob(MyJob2.class).withIdentity("myJob2").storeDurably().build();
    }

    // 把jobDetail注册到Cron表达式的trigger上去
    @Bean
    public Trigger CronJobTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?");

        return TriggerBuilder.newTrigger()
                .forJob(myCronJobDetail())
                .withIdentity("myJob2Trigger")
                .withSchedule(cronScheduleBuilder)
                .build();
    }
}
