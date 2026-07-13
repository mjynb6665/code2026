package com.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Comment {

    /** 主键ID */
    private Integer id;
    /** 商品ID */
    private Integer goodsId;
    /** 用户ID */
    private Integer userId;
    /** 用户名称 */
    private String userName;
    /** 用户头像 */
    private String userAvatar;
    /** 评论内容 */
    private String content;
    /** 评分（1-5星） */
    private Integer rating;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getGoodsId() { return goodsId; }
    public void setGoodsId(Integer goodsId) { this.goodsId = goodsId; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserAvatar() { return userAvatar; }
    public void setUserAvatar(String userAvatar) { this.userAvatar = userAvatar; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public Date getTime() { return time; }
    public void setTime(Date time) { this.time = time; }
}