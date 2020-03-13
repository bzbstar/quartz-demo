package com.bzb.quartz.quartzdemo.quartzconfig;

import com.bzb.quartz.quartzdemo.exception.QuartzException;
import com.bzb.quartz.quartzdemo.model.TaskEntity;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;

/**
 * @ClassName JobUtil
 * @Description job工具类
 * @Author baizhibin
 * @Date 2020/3/11 18:23
 * @Version 1.0
 */
@Service
public class JobUtils {

    @Autowired
    @Qualifier("scheduler")
    private Scheduler scheduler;

    /**
     * 新建一个任务
     */
    public boolean addJob(TaskEntity task) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String jobName = task.getJobName(),
                    jobGroup = task.getJobGroup(),
                    jobDescription = task.getJobDescription(),
                    startTime = task.getStartTime(),
                    endTime = task.getEndTime();
            if (checkExists(jobName, jobGroup)) {
                throw new QuartzException(String.format("Job已经存在, jobName:{%s},jobGroup:{%s}", jobName, jobGroup));
            }
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);

            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCronExpression()).
                    withMisfireHandlingInstructionDoNothing();

            TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger().withIdentity(triggerKey).
                    withDescription(jobDescription);
            if (StringUtils.hasText(startTime)) {
                triggerBuilder = triggerBuilder.startAt(sdf.parse(startTime));
            }

            if (StringUtils.hasText(endTime)) {
                triggerBuilder = triggerBuilder.endAt(sdf.parse(endTime));
            }
            CronTrigger trigger = (CronTrigger) triggerBuilder.withSchedule(scheduleBuilder).build();

            //传递参数
            if (task.getParams() != null && !"".equals(task.getParams())) {
                trigger.getJobDataMap().put("params", task.getParams());
            }

            Class<? extends Job> clazz = (Class<? extends Job>) Class.forName(task.getJobClass());
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(jobKey).withDescription(jobDescription).build();
            scheduler.scheduleJob(jobDetail, trigger);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new QuartzException(e.getMessage());
        }

    }

    /**
     * 获取Job状态
     *
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public String getJobState(String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(jobName, jobGroup);
        return scheduler.getTriggerState(triggerKey).name();
    }

    /**
     * 暂停所有任务
     */
    public boolean pauseAllJob() throws SchedulerException {
        scheduler.pauseAll();
        return true;
    }

    /**
     * 暂停（止）任务
     */
    public boolean pauseJob(String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        try {
            if (checkExists(jobName, jobGroup)) {
                scheduler.pauseTrigger(triggerKey); //停止触发器
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new QuartzException(e.getMessage());
        }
    }

    /**
     * 恢复所有任务
     */
    public boolean resumeAllJob() throws SchedulerException {
        scheduler.resumeAll();
        return true;
    }

    /**
     * 恢复某个任务
     */

    public boolean resumeJob(String jobName, String jobGroup) throws SchedulerException {

        try {
            scheduler.resumeJob(JobKey.jobKey(jobName, jobGroup));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new QuartzException(e.getMessage());
        }
    }

    /**
     * 删除某个任务
     */
    public boolean deleteJob(String jobName, String jobGroup) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            if (checkExists(jobName, jobGroup)) {
                scheduler.pauseTrigger(triggerKey); //停止触发器
                scheduler.unscheduleJob(triggerKey); //移除触发器
                return true;
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new QuartzException(e.getMessage());
        }
        return false;
    }

    /**
     * 修改任务
     */
    public boolean modifyJob(TaskEntity task) {
        try {
            String jobName = task.getJobName(),
                    jobGroup = task.getJobGroup(),
                    cronExpression = task.getCronExpression(),
                    jobDescription = task.getJobDescription(),
                    startTime = task.getStartTime(),
                    endTime = task.getEndTime();
            if (!CronExpression.isValidExpression(cronExpression)) {
                throw new QuartzException("Illegal cron expression");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            JobKey jobKey = new JobKey(jobName, jobGroup);
            if (scheduler.checkExists(jobKey) && scheduler.checkExists(triggerKey)) {
                CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                //表达式调度构建器,不立即执行
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
                //按新的cronExpression表达式重新构建trigger
                TriggerBuilder triggerBuilder = trigger.getTriggerBuilder().withIdentity(triggerKey).
                        withDescription(jobDescription);
                if (StringUtils.hasText(startTime)) {
                    triggerBuilder = triggerBuilder.startAt(sdf.parse(startTime));
                }

                if (StringUtils.hasText(endTime)) {
                    triggerBuilder = triggerBuilder.endAt(sdf.parse(endTime));
                }
                trigger = (CronTrigger) triggerBuilder.withSchedule(scheduleBuilder).build();

                //修改参数
                if (!trigger.getJobDataMap().get("params").equals(task.getParams())) {
                    trigger.getJobDataMap().put("params", task.getParams());
                }

                //按新的trigger重新设置job执行
                scheduler.rescheduleJob(triggerKey, trigger);
                return true;
            } else {
                throw new QuartzException("job or trigger not exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new QuartzException("类名不存在或执行表达式错误");
        }

    }

    /**
     * 验证是否存在
     *
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException
     */
    private boolean checkExists(String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        return scheduler.checkExists(triggerKey);
    }

}
