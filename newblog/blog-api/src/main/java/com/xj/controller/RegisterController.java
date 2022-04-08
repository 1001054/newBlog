package com.xj.controller;

import com.xj.service.LoginService;
import com.xj.vo.Result;
import com.xj.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result register(@RequestBody LoginParams loginParams){
        return loginService.register(loginParams);
    }
}
