package com.xj.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xj.mapper.PermissionMapper;
import com.xj.model.params.PageParam;
import com.xj.pojo.Permission;
import com.xj.vo.PageResult;
import com.xj.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    public Result listPermission(PageParam pageParam) {
        Page<Permission> page = new Page<>(pageParam.getCurrentPage(),pageParam.getPageSize());
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(pageParam.getQueryString())){
            queryWrapper.eq(Permission::getName, pageParam.getQueryString());
        }
        Page<Permission> permissionPage = permissionMapper.selectPage(page, queryWrapper);
        PageResult<Permission> pageResult = new PageResult<>();
        pageResult.setList(permissionPage.getRecords());
        pageResult.setTotal(permissionPage.getTotal());
        return Result.success(pageResult);
    }

    public Result add(Permission permission) {
        permissionMapper.insert(permission);
        return Result.success("插入成功");
    }


    public Result update(Permission permission) {
        permissionMapper.updateById(permission);
        return Result.success("修改成功");
    }

    public Result delete(Long id) {
        permissionMapper.deleteById(id);
        return Result.success("删除成功");
    }
}
