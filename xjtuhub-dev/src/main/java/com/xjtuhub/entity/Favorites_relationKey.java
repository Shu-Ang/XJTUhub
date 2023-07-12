package com.xjtuhub.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Favorites_relationKey {

    private Integer favoriteId;
    private Integer blogId;
    private Blog blog;

}