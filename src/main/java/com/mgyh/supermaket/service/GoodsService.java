package com.mgyh.supermaket.service;

import com.alibaba.fastjson.JSONObject;
import com.mgyh.supermaket.entity.Goods;
import com.mgyh.supermaket.repository.GoodsRepository;
import com.mgyh.supermaket.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

/**
 * @author MGYH
 * @date 2020/3/22
 */
@Service
public class GoodsService {
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private SearchRepository searchRepository;

    public List<Goods> getGoodsByCode(String code){
        return goodsRepository.findByCode(code);
    }


    public Object findAll(JSONObject object, int pageNo, int pageSize){
        String sql = "select g from Goods g where 1=1 ";
        if(object.containsKey("name")){
            sql+=" and name like :name";
            object.put("name","%"+object.getString("name")+"%");
        }
        if(object.containsKey("treeString")){
            sql+="treeString like :treeString";
            object.put("treeString","%"+object.getString("treeString")+"%");
        }
        return searchRepository.commonSearch(sql,object,pageNo,pageSize);
    }

    @Transactional
    public void save(Goods goods){
        goodsRepository.save(goods);
    }
}
