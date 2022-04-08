package com.xj.controller;

import com.xj.common.aop.LogAnnotation;
import com.xj.common.cache.Cache;
import com.xj.service.ArticleService;
import com.xj.vo.Result;
import com.xj.vo.params.ArticleParam;
import com.xj.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    @LogAnnotation(module="文章",operator="获取文章列表")
    @Cache(expire = 5 * 60 * 1000, name = "listArticle")
    public Result listArticle(@RequestBody PageParams pageParams){
        return articleService.listArticle(pageParams);
    }


    /**
     * 最热文章
     * @return
     */
    @PostMapping("hot")
    @Cache(expire = 5 * 60 * 1000, name = "hot_article")
    public Result hotArticle(){
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    /**
     * 最新文章
     * @return
     */
    @PostMapping("new")
    @Cache(expire = 5 * 60 * 1000, name = "new_article")
    public Result newArticle(){
        int limit = 5;
        return articleService.newArticle(limit);
    }

    /**
     * 文章归档
     * @return
     */
    @PostMapping("listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }

    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long id){
        return articleService.findArticleById(id);
    }

    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }
}
