package com.xj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xj.pojo.Admin;
import com.xj.pojo.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AdminMapper extends BaseMapper<Admin> {
    @Select("SELECT * FROM ms_permission where id in (select permission_id from ms_admin_permission where admin_id = #{adminId})")
    List<Permission> findPermissionsByAdminId(Long adminId);
}
