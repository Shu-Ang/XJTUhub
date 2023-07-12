package com.xjtuhub.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class FavoriteBlog {
    private Integer favoriteId;
    private Integer blogId;
    private Blog blog;
}
