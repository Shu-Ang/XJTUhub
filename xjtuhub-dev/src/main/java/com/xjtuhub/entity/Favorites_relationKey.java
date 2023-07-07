package com.xjtuhub.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Favorites_relationKey {
    private Integer favoriteId;

    private Integer blogId;
    private Blog blog;

    public Integer getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(Integer favoriteId) {
        this.favoriteId = favoriteId;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }
}