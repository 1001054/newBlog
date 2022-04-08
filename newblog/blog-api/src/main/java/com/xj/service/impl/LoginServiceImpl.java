package com.xj.service.impl;

import com.alibaba.fastjson.JSON;
import com.xj.pojo.SysUser;
import com.xj.service.LoginService;
import com.xj.service.SysUserService;
import com.xj.utils.JWTUtils;
import com.xj.vo.ErrorCode;
import com.xj.vo.Result;
import com.xj.vo.params.LoginParams;
import net.sf.jsqlparser.statement.select.KSQLJoinWindow;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //加密盐
    private static final String slat = "ssdlh!@#";

    @Override
    public Result login(LoginParams loginParams) {
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        //查看数据是否合法
        if(StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        //将密码进行加密处理
        password = DigestUtils.md5Hex(password + slat);
        //获取用户信息
        SysUser sysUser = sysUserService.findUser(account, password);
        if(sysUser == null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST);
        }
        //将信息转换为token
        String token = JWTUtils.createToken(sysUser.getId());
        //将token放入redis，并设置过期时间
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        //返回token
        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        //Checks if a CharSequence is empty (""), null or whitespace only
        if(StringUtils.isBlank(token)){
            return null;
        }
        //用工具类检查token
        if( JWTUtils.checkToken(token) == null ){
            return null;
        }
        //检查redis中是否有token
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if(StringUtils.isBlank(userJson)){
            //没有的话从mysql中查询
            SysUser sysUser = sysUserService.findUserByToken(token);
            if(sysUser == null){
                return null;
            }else{
                //mysql中有的话放入到redis中
                redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser));
                return sysUser;
            }
        }else{
            return JSON.parseObject(userJson,SysUser.class);
        }
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_" + token);
        return Result.success(null);
    }

    @Override
    public Result register(LoginParams loginParams) {
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        String nickname = loginParams.getNickname();
        //检查参数是否为空
        if ( StringUtils.isBlank(account)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname) ){
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        //检查账户是否存在
        SysUser sysUser = sysUserService.findUserByAccount(account);
        if(sysUser != null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST);
        }
        //创建新用户
        SysUser newUser = new SysUser();
        newUser.setAccount(account);
        newUser.setPassword(DigestUtils.md5Hex(password + slat));
        newUser.setNickname(nickname);
        newUser.setCreateDate(System.currentTimeMillis());
        newUser.setLastLogin(System.currentTimeMillis());
        newUser.setAvatar("/static/img/logo.b3a48c0.png");
        newUser.setAdmin(1);
        newUser.setDeleted(0);
        newUser.setSalt("");
        newUser.setStatus("");
        newUser.setEmail("112334");
        //存储新用户
        sysUserService.save(newUser);
        //将token存入redis
        String token = JWTUtils.createToken(newUser.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(newUser), 1, TimeUnit.DAYS);
        return Result.success(token);
    }

}
