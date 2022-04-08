package com.xj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xj.mapper.CategoryMapper;
import com.xj.pojo.Category;
import com.xj.service.CategoryService;
import com.xj.vo.CategoryVo;
import com.xj.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public Category findCategoryById(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        return category;
    }

    @Override
    public Result findAll() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Category::getId, Category::getCategoryName);
        List<Category> categories = categoryMapper.selectList(queryWrapper);
        return Result.success(copyList(categories));
    }

    @Override
    public Result findAllDetail() {
        List<Category> categories = categoryMapper.selectList(new LambdaQueryWrapper<Category>());
        return Result.success(copyList(categories));
    }

    @Override
    public Result findDetailById(Long id) {
        Category category = categoryMapper.selectById(id);
        return Result.success(copy(category));
    }

    private List<CategoryVo> copyList(List<Category> categories) {
        List<CategoryVo> list = new ArrayList<>();
        for (Category category : categories) {
            list.add(copy(category));
        }
        return list;
    }

    private CategoryVo copy(Category category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        categoryVo.setId(String.valueOf(category.getId()));
        return categoryVo;
    }
}
