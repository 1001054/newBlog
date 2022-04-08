package com.xj.service;

import com.xj.pojo.SysUser;
import com.xj.vo.Result;
import com.xj.vo.params.LoginParams;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LoginService {

    Result login(LoginParams loginParams);

    SysUser checkToken(String token);

    Result logout(String token);

    Result register(LoginParams loginParams);

}
