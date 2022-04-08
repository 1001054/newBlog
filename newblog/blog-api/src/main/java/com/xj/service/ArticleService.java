package com.xj.service;

import com.xj.vo.Result;
import com.xj.vo.params.ArticleParam;
import com.xj.vo.params.PageParams;

public interface ArticleService {

    Result listArticle(PageParams pageParams);

    Result hotArticle(int limit);

    Result newArticle(int limit);

    Result listArchives();

    Result findArticleById(Long id);

    Result publish(ArticleParam articleParam);
}
