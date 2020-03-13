package com.bzb.quartz.quartzdemo.service.impl;

import com.bzb.quartz.quartzdemo.mapper.UserMapper;
import com.bzb.quartz.quartzdemo.model.User;
import com.bzb.quartz.quartzdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getUserList() {
        return userMapper.getUserList();
    }
}

