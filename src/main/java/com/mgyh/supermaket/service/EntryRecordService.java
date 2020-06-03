package com.mgyh.supermaket.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mgyh.supermaket.entity.EntryRecords;
import com.mgyh.supermaket.entity.EntryRecordsDetail;
import com.mgyh.supermaket.entity.Goods;
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
import java.util.Map;


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
    public JSONArray saveRecord(EntryRecords entryRecords) {
        EntryRecords result = entryRecordsRepository.save(entryRecords);
        String date = new SimpleDateFormat("yyyyMMdd").format(entryRecords.getOperateDate());
        long dateNum = Long.parseLong(date+String.format("%02d", result.getId())) * 1000;
        JSONArray jsonArray = new JSONArray();
        Map map;
        EntryRecordsDetail entryRecordsDetail;
        for(int i = 0; i < entryRecords.getDetailList().size(); i++){
            entryRecordsDetail =  entryRecords.getDetailList().get(i);
            entryRecordsDetail.setEntryCode(dateNum + i + "");
            goodsService.entryGoods(entryRecordsDetail.getGoodsCode(),entryRecordsDetail.getGoodsNum());
            entryRecordsDetailRepository.save(entryRecordsDetail);
            for (int j = 0; j < Integer.valueOf(entryRecordsDetail.getGoodsNum()); j++) {
                map = new HashMap();
                map.put("name",entryRecordsDetail.getGoodsName());
                map.put("code",entryRecordsDetail.getEntryCode());
                jsonArray.add(map);
            }
        }
        return jsonArray;
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
                "erd.purchasePrice as price,date_format(er.operateDate,'%Y-%c-%d %h:%i:%s') as date,er.operatorName as operator,er.operatorId as operatorId," +
                "c.name as treeName,g.treeString as treeString, erd.currentNum as currentNum,erd.entryCode as entryCode," +
                "TO_DAYS(case when g.shelfLife > 0 and erd.manufactureDate is not null" +
                "        then (case g.shelfLifeUnit when '年' then date_add(erd.manufactureDate, interval g.shelfLife year)" +
                "              when '月' then date_add(erd.manufactureDate, interval g.shelfLife month)" +
                "              when '日' then date_add(erd.manufactureDate, interval g.shelfLife day)" +
                "                else 0 end) else 0 end)-TO_DAYS(NOW()) as remainingDays "+
                "from t_entryRecordsDetail erd left join t_entryRecords er on erd.recordId = er.id " +
                "left join t_goods g on g.code = erd.goodsCode " +
                "left join t_categories c on g.treeString like concat('%',c.id)"+
                "where erd.currentNum > 0 ";
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

    public void sellGoods(String code, int num){
        EntryRecordsDetail entryRecordsDetail = entryRecordsDetailRepository.findByEntryCode(code).get(0);
        entryRecordsDetail.setCurrentNum(entryRecordsDetail.getCurrentNum()- num > 0? entryRecordsDetail.getCurrentNum()- num :0);
        entryRecordsDetailRepository.save(entryRecordsDetail);
    }
}
