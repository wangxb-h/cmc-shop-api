package com.fh.service.impl;

import com.fh.dao.TypeMapper;
import com.fh.entity.TypeInfo;
import com.fh.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeMapper typeMapper;

    @Override
    public List<TypeInfo> findType() {
        List typeList=typeMapper.selectList(null);
        return typeList;
    }

    @Override
    public TypeInfo toUpdate(Integer id) {
        return typeMapper.selectById(id);
    }

    @Override
    public void update(TypeInfo typeInfo) {
        typeMapper.updateById(typeInfo);
    }

    @Override
    public void delete(Integer id) {
        typeMapper.deleteById(id);
    }
}
