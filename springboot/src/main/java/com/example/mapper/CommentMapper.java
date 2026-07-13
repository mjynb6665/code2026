package com.example.mapper;

import com.example.entity.Comment;

import java.util.List;

public interface CommentMapper {

    int insert(Comment comment);

    int deleteById(Integer id);

    Comment selectById(Integer id);

    List<Comment> selectAll(Comment comment);
}