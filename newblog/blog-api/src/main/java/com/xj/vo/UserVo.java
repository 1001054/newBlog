package com.xj.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class UserVo {

    private String nickname;

    private String avatar;

    //由于雪花算法得到的id过于长
    //使得前端得到的id精度缺失
//    @JsonSerialize(using = ToStringSerializer.class)
    private String id;
}
