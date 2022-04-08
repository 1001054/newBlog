package com.xj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xj.mapper.SysUserMapper;
import com.xj.pojo.SysUser;
import com.xj.service.LoginService;
import com.xj.service.SysUserService;
import com.xj.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private LoginService loginService;

    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if(sysUser == null){
            sysUser = new SysUser();
            sysUser.setNickname("unknown");
        }
        return sysUser;
    }

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getAccount, account);
        lambdaQueryWrapper.eq(SysUser::getPassword,password);
        lambdaQueryWrapper.select(SysUser::getAccount, SysUser::getId, SysUser::getAvatar, SysUser::getNickname);
        lambdaQueryWrapper.last("limit 1");
        return sysUserMapper.selectOne(lambdaQueryWrapper);
    }

    @Override
    public SysUser findUserByToken(String token) {
        SysUser sysUser = loginService.checkToken(token);
        return sysUser;
    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getAccount, account);
        lambdaQueryWrapper.last("limit 1");
        return sysUserMapper.selectOne(lambdaQueryWrapper);
    }

    @Override
    public void save(SysUser newUser) {
        //id自动生成，分布式id 雪花算法
        sysUserMapper.insert(newUser);
    }

    @Override
    public UserVo findUserVoById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if(sysUser == null){
            sysUser = new SysUser();
            sysUser.setAvatar("/static/img/logo.b3a48c0.png");
            sysUser.setNickname("默认用户");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(sysUser, userVo);
        userVo.setId(String.valueOf(sysUser.getId()));
        return userVo;
    }
}
