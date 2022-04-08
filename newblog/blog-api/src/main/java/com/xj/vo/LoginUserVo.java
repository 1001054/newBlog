package com.xj.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.xj.pojo.SysUser;
import lombok.Data;

@Data
public class LoginUserVo {

    //由于雪花算法得到的id过于长
    //使得前端得到的id精度缺失
//    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String account;

    private String nickname;

    private String avatar;

    public LoginUserVo() {
    }

    public LoginUserVo(SysUser sysUser){
        id = String.valueOf(sysUser.getId());
        account = sysUser.getAccount();
        nickname = sysUser.getNickname();
        avatar = sysUser.getAvatar();
    }
}
