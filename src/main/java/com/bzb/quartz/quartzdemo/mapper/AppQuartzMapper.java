package com.bzb.quartz.quartzdemo.mapper;

import com.bzb.quartz.quartzdemo.model.AppQuartz;

public interface AppQuartzMapper {
    int deleteByPrimaryKey(Integer quartzId);

    int insert(AppQuartz record);

    int insertSelective(AppQuartz record);

    AppQuartz selectByPrimaryKey(Integer quartzId);

    int updateByPrimaryKeySelective(AppQuartz record);

    int updateByPrimaryKey(AppQuartz record);
}