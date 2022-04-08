package com.xj.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xj.mapper.SysUserMapper;
import com.xj.model.params.PageParam;
import com.xj.pojo.SysUser;
import com.xj.vo.PageResult;
import com.xj.vo.Result;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class SysUserService {
    //加密盐
    private static final String slat = "ssdlh!@#";
    @Autowired
    private SysUserMapper sysUserMapper;

    public Result userList(PageParam pageParam){
        Page<SysUser> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(pageParam.getQueryString())){
            queryWrapper.eq(SysUser::getAccount, pageParam.getQueryString());
        }
        Page<SysUser> userPage = sysUserMapper.selectPage(page, queryWrapper);
        PageResult<SysUser> pageResult = new PageResult<>();
        pageResult.setList(userPage.getRecords());
        pageResult.setTotal(userPage.getTotal());
        return Result.success(pageResult);
    }

    public Result userUpdate(SysUser sysUser) {
        String password = sysUser.getPassword();
        if(StringUtils.isBlank(password)){
            return Result.fail(409,"密码不能为空");
        }
        sysUser.setPassword(DigestUtils.md5Hex(password));
        sysUserMapper.updateById(sysUser);
        return Result.success("修改成功");
    }

    public Result userAdd(SysUser sysUser) {
        String password = sysUser.getPassword();
        if(StringUtils.isBlank(password)){
            return Result.fail(409,"密码不能为空");
        }
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setPassword(DigestUtils.md5Hex(password));
        sysUserMapper.insert(sysUser);
        return Result.success("插入成功");
    }

    public Result userDelete(Long id) {
        sysUserMapper.deleteById(id);
        return Result.success("删除成功");
    }
}
