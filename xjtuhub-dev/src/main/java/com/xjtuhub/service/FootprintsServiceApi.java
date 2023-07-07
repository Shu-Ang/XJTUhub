package com.xjtuhub.service;

import com.xjtuhub.entity.Footprints;

import java.util.List;

public interface FootprintsServiceApi {
    List<Footprints> selectFootprintsByRoleId(String roleId);
}
