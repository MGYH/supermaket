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

    public List getGoodsByEntryCode(String code){
        String sql = "select g.code as code,g.name as name,g.price as price, te.entryCode as entryCode,te.detailId as id," +
                "te.purchasePrice as purchasePrice " +
                " from t_goods g" +
                " left join t_EntryRecordsDetail te on g.code = te.goodsCode "+
                "where te.entryCode = "+code;
        return searchRepository.sqlSearch(sql);
    }

    public JSONObject getGoodsList(JSONObject object, int pageNo, int pageSize){
        return searchRepository.commonSearch(getGoodsListSql(object),object,pageNo,pageSize);
    }
    public JSONObject getGoodsList(JSONObject object){
        return searchRepository.commonSearch(getGoodsListSql(object),object);
    }
    public String getGoodsListSql(JSONObject object){
        String sql = "select new map(g.id as id,g.code as code,g.name as name," +
                "g.num as num,g.treeString as treeString,g.price as price,g.version as version," +
                "g.shelfLife as shelfLife,shelfLifeUnit as shelfLifeUnit) " +
                "from Goods g where 1=1 ";
        if(object.containsKey("code")){
            sql+=" and code = :code";
        }
        if(object.containsKey("name")){
            sql+=" and name like :name";
            object.put("name","%"+object.getString("name")+"%");
        }
        if(object.containsKey("treeString")){
            sql+=" and treeString like :treeString";
            object.put("treeString","%"+object.getString("treeString")+"%");
        }
        return sql;
    }

    @Transactional(rollbackOn = Exception.class)
    public void save(Goods goods){
        goodsRepository.save(goods);
    }

    @Transactional(rollbackOn = Exception.class)
    public void entryGoods(String code, int num){
        Goods goods = goodsRepository.findByCode(code).get(0);
        goods.setNum(goods.getNum() + num);
        goodsRepository.save(goods);
    }

    public void sellGoods(String code, int num){
        Goods goods = goodsRepository.findByCode(code).get(0);
        goods.setNum(goods.getNum()-num > 0 ? goods.getNum()-num : 0);
        goodsRepository.save(goods);
    }

    public JSONObject getPieChart(JSONObject object) {
        String group = object.getString("group");
        String sql = "select r.name as name, r.value as value from ( select "+group+" as name, cast(IFNULL(sum(g.num),0) as char) as value " +
                "from t_goods g left join t_categories tc on g.treeString like concat('%',tc.id) " +
                "group by "+group+" ) r order by r.value desc";
        return searchRepository.sqlSearch(sql,new JSONObject());
    }
}
