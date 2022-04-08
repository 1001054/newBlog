package com.xj.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xj.mapper.ArticleMapper;
import com.xj.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {

    //期望此操作在线程池 执行 不会影响原有的主线程
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article){
        int viewCount = article.getViewCounts();
        //更新规则是实体类中有什么属性就更新什么属性
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(viewCount + 1);
        //规定要更新的数据
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId, article.getId());
        updateWrapper.eq(Article::getViewCounts, viewCount);
        //更新
        articleMapper.update(articleUpdate, updateWrapper);

    }
}
