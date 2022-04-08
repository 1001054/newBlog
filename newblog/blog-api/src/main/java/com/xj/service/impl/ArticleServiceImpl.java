package com.xj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xj.dos.Archives;
import com.xj.mapper.*;
import com.xj.pojo.*;
import com.xj.service.*;
import com.xj.utils.UserThreadLocal;
import com.xj.vo.*;
import com.xj.vo.params.ArticleBodyParam;
import com.xj.vo.params.ArticleParam;
import com.xj.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagService tagService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ThreadService threadService;
    @Autowired
    private ArticleTagMapper articleTagMapper;



//    @Override
//    public Result listArticle(PageParams pageParams) {
//        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
//
//        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        //如果分类的话，只查找该类别
//        Long categoryId = pageParams.getCategoryId();
//        if(categoryId != null){
//            lambdaQueryWrapper.eq(Article::getCategoryId, categoryId);
//        }
//        //如果有标签的话，只显示该标签的文章
//        Long tagId = pageParams.getTagId();
//        if(tagId != null){
//            List<Long> articleIds = new ArrayList<>();
//            LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
//            queryWrapper.eq(ArticleTag::getTagId, tagId);
//            List<ArticleTag> articleTags = articleTagMapper.selectList(queryWrapper);
//            for (ArticleTag articleTag : articleTags) {
//                articleIds.add(articleTag.getArticleId());
//            }
//            if(articleIds.size() > 0){
//                lambdaQueryWrapper.in(Article::getId, articleIds);
//            }
//        }
//        //排序
//        lambdaQueryWrapper.orderByDesc(Article::getWeight, Article::getCreateDate);
//        Page<Article> articlePage = articleMapper.selectPage(page, lambdaQueryWrapper);
//        //转换形式
//        List<Article> records = articlePage.getRecords();
//        List<ArticleVo> articleVoList = copyList(records, true, true);
//        return Result.success(articleVoList);
//    }

    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<Article> iPage = articleMapper.listArticle(
                page,
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth());
        List<Article> records = iPage.getRecords();
        return Result.success(copyList(records, true, true));
    }

    @Override
    public Result hotArticle(int limit) {
        //查询语句
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Article::getViewCounts);
        lambdaQueryWrapper.select(Article::getId, Article::getTitle);
        lambdaQueryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(lambdaQueryWrapper);
        //转换形式
        List<ArticleVo> articleVoList = copyList(articles, false, false);
        return Result.success(articleVoList);
    }

    @Override
    public Result newArticle(int limit) {
        //查询语句
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Article::getCreateDate);
        lambdaQueryWrapper.select(Article::getId, Article::getTitle);
        lambdaQueryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(lambdaQueryWrapper);
        //转换形式
        List<ArticleVo> articleVoList = copyList(articles, false, false);
        return Result.success(articleVoList);
    }

    @Override
    public Result listArchives() {
        List<Archives> archives = articleMapper.listArchives();
        return Result.success(archives);
    }

    @Override
    public Result findArticleById(Long id) {
        Article article = articleMapper.selectById(id);
        ArticleVo articleVo = copy(article,true,true, true, true);
        //更新浏览量
        //线程池：可以把更新操作放入到线程池中，不会影响主线程
        threadService.updateArticleViewCount(articleMapper, article);
        return Result.success(articleVo);
    }

    @Override
    public Result publish(ArticleParam articleParam) {
        SysUser sysUser = UserThreadLocal.get();

        //将传进的参数转换形式
        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setViewCounts(0);
        article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
        article.setSummary(articleParam.getSummary());
        article.setCreateDate(System.currentTimeMillis());
        article.setTitle(articleParam.getTitle());
        article.setWeight(Article.Article_Common);
        article.setCommentCounts(0);

        //文章插入后会生成id
        articleMapper.insert(article);

        //article tag 关系表中插入数据
        Long articleId = article.getId();
        List<TagVo> tags = articleParam.getTags();
        if (tags != null){
            for (TagVo tag : tags) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(articleId);
                articleTag.setTagId(Long.parseLong(tag.getId()));
                articleTagMapper.insert(articleTag);
            }
        }

        //将 articlebody 插入数据库
        ArticleBody articleBody = new ArticleBody();
        ArticleBodyParam articleBodyParam = articleParam.getBody();
        articleBody.setArticleId(articleId);
        articleBody.setContent(articleBodyParam.getContent());
        articleBody.setContentHtml(articleBodyParam.getContentHtml());
        articleBodyMapper.insert(articleBody);

        //得到bodyid更新article表
        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);
        Map<String, String> map = new HashMap<>();
        map.put("id",article.getId().toString());
        return Result.success(map);
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for(Article article : records){
            articleVoList.add(copy(article, isTag, isAuthor,false,false));
        }
        return articleVoList;
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for(Article article : records){
            articleVoList.add(copy(article, isTag, isAuthor,isBody,isCategory));
        }
        return articleVoList;
    }

    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);
        articleVo.setId(String.valueOf(article.getId()));
        //设置时间格式
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //加标签和作者
        if(isTag){
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if(isAuthor){
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }
        if(isBody){
            Long bodyId = article.getBodyId();
            articleVo.setBody(findBodyById(bodyId));
        }
        if(isCategory){
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(findCategoryById(categoryId));
        }
        return articleVo;
    }

    private CategoryVo findCategoryById(Long categoryId) {
        Category category = categoryService.findCategoryById(categoryId);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        categoryVo.setId(String.valueOf(category.getId()));
        return categoryVo;
    }

    private ArticleBodyVo findBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }
}
