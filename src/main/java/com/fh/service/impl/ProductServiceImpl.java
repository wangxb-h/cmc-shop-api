package com.fh.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.fh.dao.ProductMapper;
import com.fh.entity.AreaInfo;
import com.fh.entity.ProductInfo;
import com.fh.service.ProductService;
import com.fh.utils.RedisUse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductInfo> findProductByTypeId(ProductInfo product) {
        List list=productMapper.findProductByTypeId(product);
        return list;
    }

    @Override
    public List<ProductInfo> findHotAll() {
        return productMapper.findHotAll();
    }

    @Override
    public List<ProductInfo> findProductAllByProductId(Integer id) {
        List<ProductInfo> list=productMapper.findProductAllByProductId(id);
        String areaShop_json_wxb = RedisUse.get("areaShop_json_wxb");
        List<AreaInfo> arealist = JSONArray.parseArray(areaShop_json_wxb, AreaInfo.class);
        for (int i = 0; i < list.size(); i++) {
            ProductInfo productInfo = list.get(i);
            String areaIds = productInfo.getAreaIds();
            String substring = areaIds.substring(1, areaIds.length() - 1);
            String[] Areasplit = substring.split(",");
            String areaNames="";
            Integer areaId=0;
            for (int j = 0; j < arealist.size(); j++) {
                if (areaId<3 && String.valueOf(arealist.get(j).getId()).equals(Areasplit[areaId])){
                    String areaName=arealist.get(j).getAreaName();
                    areaNames += areaName + ",";
                    areaId++;
                }else {
                    continue;
                }
            }
            productInfo.setAreaName(areaNames.substring(0,areaNames.length()-1));
        }
        return list;
    }
}
