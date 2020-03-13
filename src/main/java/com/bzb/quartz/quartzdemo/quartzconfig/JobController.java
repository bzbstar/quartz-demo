package com.bzb.quartz.quartzdemo.quartzconfig;

import com.bzb.quartz.quartzdemo.model.TaskEntity;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobUtils jobUtils;

    @RequestMapping(value = "/addJob", method = RequestMethod.POST)
    public boolean addjob(@RequestBody TaskEntity taskEntity) throws Exception {
        return jobUtils.addJob(taskEntity);
    }

    /**
     * 获取Job状态
     *
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    @PostMapping("/getJobState/{jobName}/{jobGroup}")
    public String getJobState(@PathVariable("jobName") String jobName, @PathVariable("jobGroup") String jobGroup) throws SchedulerException {
        return jobUtils.getJobState(jobName, jobGroup);
    }

    /**
     * 暂停所有任务
     */
    @PostMapping("/pauseAllJob")
    public boolean pauseAllJob() throws SchedulerException {
        return jobUtils.pauseAllJob();
    }

    /**
     * 暂停job
     */
    @RequestMapping(value = "/pauseJob/{jobName}/{jobGroup}", method = RequestMethod.POST)
    public boolean pauseJob(@PathVariable("jobName") String jobName,
                            @PathVariable("jobGroup") String jobGroup) throws Exception {
        return jobUtils.pauseJob(jobName, jobGroup);
    }

    /**
     * 恢复所有任务
     */
    @PostMapping("/resumeAllJob")
    public boolean resumeAllJob() throws SchedulerException {
        return jobUtils.resumeAllJob();
    }

    /**
     * 恢复job
     */
    @PostMapping("/resumeJob/{jobName}/{jobGroup}")
    public boolean resumejob(@PathVariable("jobName") String jobName,
                             @PathVariable("jobGroup") String jobGroup) throws Exception {
        return jobUtils.resumeJob(jobName, jobGroup);
    }


    /**
     * 删除某个任务
     */
    @PostMapping("/deleteJob/{jobName}/{jobGroup}")
    public boolean deleteJob(@PathVariable("jobName") String jobName,
                             @PathVariable("jobGroup") String jobGroup) {
        return jobUtils.deleteJob(jobName, jobGroup);
    }

    /**
     * 修改
     */
    @PostMapping(value = "/updateJob")
    public boolean modifyJob(@RequestBody TaskEntity taskEntity) throws Exception {
        return jobUtils.modifyJob(taskEntity);
    }

}