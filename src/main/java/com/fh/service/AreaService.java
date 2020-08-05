package com.fh.service;

import com.fh.entity.AreaInfo;

import java.util.List;

public interface AreaService {
    List<AreaInfo> findArea();

    AreaInfo toupdate(Integer id);

    void update(AreaInfo areaInfo);
}
