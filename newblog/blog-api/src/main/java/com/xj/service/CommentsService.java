package com.xj.service;

import com.xj.vo.Result;
import com.xj.vo.params.CommentParam;

public interface CommentsService {

    Result commentsByArticleId(Long articleId);

    Result comment(CommentParam commentParam);
}
