package com.xj.vo;

import lombok.Data;

@Data
public class CategoryVo {

    //由于雪花算法得到的id过于长
    //使得前端得到的id精度缺失
//    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String avatar;

    private String categoryName;

    private String description;
}
