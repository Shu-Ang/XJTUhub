package com.xjtuhub.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Favorites {
    private Integer favoriteId;

    private String roleId;

    private String favoriteName;

    private Integer isPrivate;


}