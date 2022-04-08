package com.xj.pojo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.Data;

@Data
public class AdminPermission {
    private Long id;
    private Long adminId;
    private Long permissionId;

    public AdminPermission(Long adminId, Long permissionId) {
        this.adminId = adminId;
        this.permissionId = permissionId;
    }
}
