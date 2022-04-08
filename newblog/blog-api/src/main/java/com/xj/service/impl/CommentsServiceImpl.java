package com.xj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xj.mapper.CommentMapper;
import com.xj.pojo.Comment;
import com.xj.pojo.SysUser;
import com.xj.service.CommentsService;
import com.xj.service.SysUserService;
import com.xj.utils.UserThreadLocal;
import com.xj.vo.CommentVo;
import com.xj.vo.Result;
import com.xj.vo.UserVo;
import com.xj.vo.params.CommentParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    CommentMapper commentMapper;
    @Autowired
    SysUserService sysUserService;

    @Override
    public Result commentsByArticleId(Long articleId) {
        //先取出该文章level为1的评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, articleId);
        queryWrapper.eq(Comment::getLevel, 1);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        //转换形式
        List<CommentVo> commentVoList = copyList(comments);
        return Result.success(commentVoList);
    }

    @Override
    public Result comment(CommentParam commentParam) {
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        if(parent == null || parent == 0){
            comment.setLevel(1);
            comment.setParentId((long)0);
        }else{
            comment.setLevel(2);
            comment.setParentId(parent);
        }
        Long toUserId = commentParam.getToUserId();
        if(toUserId == null){
            comment.setToUid((long)0);
        }else{
            comment.setToUid(toUserId);
        }
        commentMapper.insert(comment);
        return Result.success("插入成功");
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> result = new ArrayList<>();
        for (Comment comment : comments) {
            result.add(copy(comment));
        }
        return result;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
        commentVo.setId(String.valueOf(comment.getId()));
        //填写作者信息
        UserVo author = sysUserService.findUserVoById(comment.getAuthorId());
        commentVo.setAuthor(author);
        //填写子评论
        Integer level = comment.getLevel();
        if(1 == level){
            List<CommentVo> commentVoList = findCommentsByParentId(comment.getId());
            commentVo.setChildrens(commentVoList);
        }
        //填写toUser
        if(level > 1){
            Long toUid = comment.getToUid();
            UserVo toUser = sysUserService.findUserVoById(toUid);
            commentVo.setToUser(toUser);
        }
        return commentVo;
    }

    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId, id);
        queryWrapper.eq(Comment::getLevel, 2);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        List<CommentVo> commentVos = copyList(comments);
        return commentVos;
    }
}
