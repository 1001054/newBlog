package com.xj.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xj.mapper.ArticleMapper;
import com.xj.model.params.PageParam;
import com.xj.pojo.Article;
import com.xj.vo.PageResult;
import com.xj.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    public Result listArticle(PageParam pageParam) {
        Page<Article> page = new Page<>(pageParam.getCurrentPage(),pageParam.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(pageParam.getQueryString())){
            queryWrapper.eq(Article::getTitle, pageParam.getQueryString());
        }
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        PageResult<Article> pageResult = new PageResult<>();
        pageResult.setTotal(articlePage.getTotal());
        pageResult.setList(articlePage.getRecords());
        return Result.success(pageResult);
    }

    public Result addArticle(Article article) {
        if(StringUtils.isBlank(article.getTitle()) || StringUtils.isBlank(article.getSummary())){
            return Result.fail(600,"标题或简介不得为空！");
        }
        articleMapper.insert(article);
        return Result.success("添加成功");
    }

    public Result updateArticle(Article article) {
        if(StringUtils.isBlank(article.getTitle()) || StringUtils.isBlank(article.getSummary())){
            return Result.fail(600,"标题或简介不得为空！");
        }
        articleMapper.updateById(article);
        return Result.success("修改成功");
    }

    public Result deleteArticle(Long id) {
        articleMapper.deleteById(id);
        return Result.success("删除成功");
    }
}
