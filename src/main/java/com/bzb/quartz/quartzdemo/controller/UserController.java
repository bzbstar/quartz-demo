package com.bzb.quartz.quartzdemo.controller;


import com.bzb.quartz.quartzdemo.model.User;
import com.bzb.quartz.quartzdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getUserList(){
        return userService.getUserList();
    }
}

