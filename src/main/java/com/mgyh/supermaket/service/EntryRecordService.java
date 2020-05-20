package com.mgyh.supermaket.service;

import com.alibaba.fastjson.JSONObject;
import com.mgyh.supermaket.entity.EntryRecords;
import com.mgyh.supermaket.entity.EntryRecordsDetail;
import com.mgyh.supermaket.repository.EntryRecordsDetailRepository;
import com.mgyh.supermaket.repository.EntryRecordsRepository;
import com.mgyh.supermaket.repository.SearchRepository;
import com.mgyh.supermaket.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;


@Service
public class EntryRecordService {

    @Autowired
    private EntryRecordsDetailRepository entryRecordsDetailRepository;

    @Autowired
    private EntryRecordsRepository entryRecordsRepository;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SearchRepository searchRepository;

    @Transactional(rollbackFor = Exception.class)
    public void saveRecord(EntryRecords entryRecords) throws Exception {
        EntryRecords result = entryRecordsRepository.save(entryRecords);
        for(EntryRecordsDetail entryRecordsDetail : entryRecords.getDetailList()){
            System.out.println(entryRecordsDetail.getGoodsNum());
            goodsService.entryGoods(entryRecordsDetail.getGoodsCode(),entryRecordsDetail.getGoodsNum());
            entryRecordsDetailRepository.save(entryRecordsDetail);
        }
    }

    public JSONObject getPieChart(JSONObject object) throws ParseException {
        String sql = getEntryGoodsListSql(object);
        int fromIndex=sql.toUpperCase().indexOf("FROM");
        sql = "select r.name,r.value from (select g.name as name, cast(IFNULL(sum(erd.goodsNum),0) as char) as value " + getEntryGoodsListSql(object).substring(fromIndex)
        +" group by g.name) r order by r.value desc";
        return searchRepository.sqlSearch(sql,new JSONObject());
    }

    public JSONObject getEntryGoodsList(JSONObject object, int pageNo, int pageSize) throws ParseException {
        return searchRepository.sqlSearch(getEntryGoodsListSql(object),object,pageNo,pageSize);
    }
    public JSONObject getEntryGoodsList(JSONObject object) throws ParseException {
        return searchRepository.sqlSearch(getEntryGoodsListSql(object),object);
    }
    public String getEntryGoodsListSql(JSONObject object) throws ParseException {
        String sql = "select er.id as id,erd.goodsCode as code,erd.goodsName as name, erd.goodsNum as num," +
                "g.purchasePrice as price,date_format(er.operateDate,'%Y-%c-%d %h:%i:%s') as date,er.operatorName as operator,er.operatorId as operatorId," +
                "c.name as treeName,g.treeString as treeString "+
                "from t_entryRecordsDetail erd left join t_entryRecords er on erd.recordId = er.id " +
                "left join t_goods g on g.code = erd.goodsCode " +
                "left join t_categories c on g.treeString like concat('%',c.id)"+
                "where 1 = 1 ";
        if(object.containsKey("treeString")){
            sql+=" and g.treeString like :treeString ";
            object.put("treeString",object.getString("treeString")+"%");
        }
        if(object.containsKey("id")){
            sql+=" and er.id = :id ";
            object.put("id",object.getString("id"));
        }
        if(object.containsKey("startDate")){
            sql+=" and er.operateDate >= :startDate ";
            object.put("startDate",new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" ).parse(object.getString("startDate")+" 00:00:00"));
        }
        if(object.containsKey("endDate")){
            sql+=" and er.operateDate <= :endDate ";
            object.put("endDate",new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" ).parse(object.getString("endDate")+" 23:59:59"));
        }
        return sql;
    }
}
