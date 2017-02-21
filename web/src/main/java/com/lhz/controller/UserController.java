package com.lhz.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by LHZ on 2017/1/16.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping(value = "/info")
    public ResponseEntity login(HttpServletRequest request){
        return ResponseEntity.ok("登录成功！");
    }
}
