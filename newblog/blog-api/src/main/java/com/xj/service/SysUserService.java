package com.xj.service;

import com.xj.pojo.SysUser;
import com.xj.vo.UserVo;

public interface SysUserService {
    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    SysUser findUserByToken(String token);

    SysUser findUserByAccount(String account);

    void save(SysUser newUser);

    UserVo findUserVoById(Long id);
}
