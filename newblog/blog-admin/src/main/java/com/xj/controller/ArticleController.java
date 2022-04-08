package com.xj.controller;

import com.xj.model.params.PageParam;
import com.xj.pojo.Article;
import com.xj.service.ArticleService;
import com.xj.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("list")
    public Result listArticle(@RequestBody PageParam pageParam){
        return articleService.listArticle(pageParam);
    }

    @PostMapping("add")
    public Result addArticle(@RequestBody Article article){
        return articleService.addArticle(article);
    }

    @PostMapping("update")
    public Result updateArticle(@RequestBody Article article){
        return articleService.updateArticle(article);
    }

    @GetMapping("delete/{id}")
    public Result deleteArticle(@PathVariable Long id){
        return articleService.deleteArticle(id);
    }
}
