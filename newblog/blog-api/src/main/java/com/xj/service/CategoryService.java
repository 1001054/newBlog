package com.xj.service;

import com.xj.pojo.Category;
import com.xj.vo.Result;

public interface CategoryService {

    Category findCategoryById(Long categoryId);

    Result findAll();

    Result findAllDetail();

    Result findDetailById(Long id);
}
