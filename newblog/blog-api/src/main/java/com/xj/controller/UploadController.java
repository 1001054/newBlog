package com.xj.controller;

import com.xj.utils.QiniuUtils;
import com.xj.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private QiniuUtils qiniuUtils;

    @PostMapping
    public Result upload(@RequestParam("image") MultipartFile file){
        //获取原始文件名称
        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID() + "." + StringUtils.substringAfterLast(originalFilename,".");
        boolean upload = qiniuUtils.upload(file, fileName);
        if(upload){
            return Result.success(QiniuUtils.url + fileName);
        }else{
            return Result.fail(20001,"上传失败");
        }

    }
}
