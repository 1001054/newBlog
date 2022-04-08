package com.xj.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xj.mapper.AdminMapper;
import com.xj.mapper.AdminPermissionMapper;
import com.xj.model.params.PageParam;
import com.xj.pojo.Admin;
import com.xj.pojo.AdminPermission;
import com.xj.pojo.Permission;
import com.xj.vo.PageResult;
import com.xj.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private AdminPermissionMapper adminPermissionMapper;

    public Admin findAdminByUsername(String username){
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername, username);
        queryWrapper.last("limit 1");
        Admin admin = adminMapper.selectOne(queryWrapper);
        return admin;
    }

    public List<Permission> findPermissionsByAdminId(Long adminId) {

        return adminMapper.findPermissionsByAdminId(adminId);
    }

    public Result listAdmin(PageParam pageParam) {
        Page<Admin> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(pageParam.getQueryString())){
            queryWrapper.eq(Admin::getUsername, pageParam.getQueryString());
        }
        Page<Admin> adminPage = adminMapper.selectPage(page, queryWrapper);
        PageResult<Admin> pageResult = new PageResult<>();
        pageResult.setList(adminPage.getRecords());
        pageResult.setTotal(adminPage.getTotal());
        return Result.success(pageResult);
    }

    public Result adminUpdate(Admin admin) {
        Admin adminDB = adminMapper.selectById(admin.getId());
        if(adminDB.getLevel() == admin.getLevel()){
            adminMapper.updateById(admin);
        }else{
            //更新用户表
            adminMapper.updateById(admin);
            //更新用户权限表
            setAdminPermission(admin);
        }
        return Result.success("更新成功");
    }

    public Result adminAdd(Admin admin) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        admin.setPassword(encoder.encode(admin.getPassword()));
        adminMapper.insert(admin);
        return Result.success("添加成功");
    }

    public Result adminDelete(Long id) {
        if(id == 1){
            return Result.fail(403,"无法删除主管理员");
        }
        adminMapper.deleteById(id);
        return Result.success("删除成功");
    }

    public void setAdminPermission(Admin admin){
        //先删掉原有的权限
        LambdaQueryWrapper<AdminPermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AdminPermission::getAdminId, admin.getId());
        adminPermissionMapper.delete(queryWrapper);

        if(admin.getLevel() > 0){
            //0等级以上权限
            adminPermissionMapper.insert(new AdminPermission(admin.getId(), new Long(16)));
            adminPermissionMapper.insert(new AdminPermission(admin.getId(), new Long(12)));
        }
        if(admin.getLevel() > 1){
            //1等级以上权限
            adminPermissionMapper.insert(new AdminPermission(admin.getId(), new Long(15)));
            adminPermissionMapper.insert(new AdminPermission(admin.getId(), new Long(14)));
            adminPermissionMapper.insert(new AdminPermission(admin.getId(), new Long(13)));
            adminPermissionMapper.insert(new AdminPermission(admin.getId(), new Long(11)));
            adminPermissionMapper.insert(new AdminPermission(admin.getId(), new Long(10)));
            adminPermissionMapper.insert(new AdminPermission(admin.getId(), new Long(9)));
        }
        if(admin.getLevel() > 2){
            adminPermissionMapper.insert(new AdminPermission(admin.getId(), new Long(8)));
            adminPermissionMapper.insert(new AdminPermission(admin.getId(), new Long(6)));
            adminPermissionMapper.insert(new AdminPermission(admin.getId(), new Long(5)));
            adminPermissionMapper.insert(new AdminPermission(admin.getId(), new Long(1)));
        }
        if(admin.getLevel() > 4){
            adminPermissionMapper.insert(new AdminPermission(admin.getId(), new Long(4)));
            adminPermissionMapper.insert(new AdminPermission(admin.getId(), new Long(3)));
            adminPermissionMapper.insert(new AdminPermission(admin.getId(), new Long(2)));
            adminPermissionMapper.insert(new AdminPermission(admin.getId(), new Long(7)));
        }
    }
}
