package com.fh.service.impl;

import com.fh.dao.AreaMapper;
import com.fh.entity.AreaInfo;
import com.fh.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaMapper areaMapper;

    @Override
    public List<AreaInfo> findArea() {
        int i=1/0;
        return areaMapper.selectList(null);
    }

    @Override
    public AreaInfo toupdate(Integer id) {
        return areaMapper.selectById(id);
    }

    @Override
    public void update(AreaInfo areaInfo) {
        areaMapper.updateById(areaInfo);
    }
}
