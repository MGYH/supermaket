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


    public JSONObject getGoodsList(JSONObject object, int pageNo, int pageSize){
        return searchRepository.commonSearch(getGoodsListSql(object),object,pageNo,pageSize);
    }
    public JSONObject getGoodsList(JSONObject object){
        return searchRepository.commonSearch(getGoodsListSql(object),object);
    }
    public String getGoodsListSql(JSONObject object){
        String sql = "select new map(g.id as id,g.code as code,g.name as name," +
                "g.num as num,g.treeString as treeString,g.price as price," +
                "g.shelfLife as shelfLife,shelfLifeUnit as shelfLifeUnit) " +
                "from Goods g where 1=1 ";
        if(object.containsKey("name")){
            sql+=" and name like :name";
            object.put("name","%"+object.getString("name")+"%");
        }
        if(object.containsKey("treeString")){
            sql+="treeString like :treeString";
            object.put("treeString","%"+object.getString("treeString")+"%");
        }
        return sql;
    }

    @Transactional(rollbackOn = Exception.class)
    public void save(Goods goods){
        goodsRepository.save(goods);
    }

    public void entryGoods(String code, String num){
        Goods goods = goodsRepository.findByCode(code).get(0);
        goods.setNum((Integer.valueOf(goods.getNum())+Integer.valueOf(num))+"");
        goodsRepository.save(goods);
    }

    public void sellGoods(String code, String num){
        Goods goods = goodsRepository.findByCode(code).get(0);
        goods.setNum((Integer.valueOf(goods.getNum())-Integer.valueOf(num))+"");
        goodsRepository.save(goods);
    }
}
