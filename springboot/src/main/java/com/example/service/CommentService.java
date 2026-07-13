package com.example.service;

import com.example.entity.Comment;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.mapper.CommentMapper;
import com.example.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {

    @Resource
    private CommentMapper commentMapper;
    @Resource
    private UserMapper userMapper;

    public void add(Comment comment) {
        if (comment.getRating() == null || comment.getRating() < 1 || comment.getRating() > 5) {
            throw new CustomException("评分必须在1-5星之间");
        }
        comment.setTime(new Date());
        commentMapper.insert(comment);
    }

    public void deleteById(Integer id) {
        commentMapper.deleteById(id);
    }

    public Comment selectById(Integer id) {
        return commentMapper.selectById(id);
    }

    public List<Comment> selectAll(Comment comment) {
        return commentMapper.selectAll(comment);
    }

    public PageInfo<Comment> selectPage(Comment comment, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> list = commentMapper.selectAll(comment);
        return PageInfo.of(list);
    }
}