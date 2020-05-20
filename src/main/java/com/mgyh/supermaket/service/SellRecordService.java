package com.mgyh.supermaket.service;

import com.alibaba.fastjson.JSONObject;
import com.mgyh.supermaket.config.PayService;
import com.mgyh.supermaket.entity.SellRecords;
import com.mgyh.supermaket.entity.SellRecordsDetail;
import com.mgyh.supermaket.repository.SearchRepository;
import com.mgyh.supermaket.repository.SellRecordsDetailRepository;
import com.mgyh.supermaket.repository.SellRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;


@Service
public class SellRecordService {

    @Autowired
    private SellRecordsDetailRepository sellRecordsDetailRepository;

    @Autowired
    private SellRecordsRepository sellRecordsRepository;
    @Autowired
    private SearchRepository searchRepository;


    @Autowired
    public PayService service;

    @Autowired
    private GoodsService goodsService;


    @Transactional(rollbackFor = Exception.class)
    public void saveRecord(SellRecords sellRecords, String authCode) throws Exception {
        SellRecords result = sellRecordsRepository.save(sellRecords);
        for(SellRecordsDetail sellRecordsDetail : sellRecords.getDetailList()){
            goodsService.sellGoods(sellRecordsDetail.getGoodsCode(),sellRecordsDetail.getGoodsNum());
            sellRecordsDetailRepository.save(sellRecordsDetail);
        }
        if(sellRecords.getPayment().equalsIgnoreCase("zfb")){
            service.alipayTradePayService(authCode,sellRecords.getTotalMoney(),result.getId()+"test");
        }
    }

    public JSONObject getPieChart(JSONObject object) throws ParseException {
        String sql = getSellGoodsListSql(object);
        int fromIndex=sql.toUpperCase().indexOf("FROM");
        sql = "select r.name,r.value from (select g.name as name, cast(IFNULL(sum(erd.goodsNum),0) as char) as value " + getSellGoodsListSql(object).substring(fromIndex)
        +" group by g.name) r order by r.value desc";
        return searchRepository.sqlSearch(sql,new JSONObject());
    }

    public JSONObject getSellGoodsList(JSONObject object, int pageNo, int pageSize) throws ParseException {
        return searchRepository.sqlSearch(getSellGoodsListSql(object),object,pageNo,pageSize);
    }
    public JSONObject getEntryGoodsList(JSONObject object) throws ParseException {
        return searchRepository.sqlSearch(getSellGoodsListSql(object),object);
    }
    public String getSellGoodsListSql(JSONObject object) throws ParseException {
        System.out.println("======");
        String sql = "select er.id as id,erd.goodsCode as code,erd.goodsName as name, erd.goodsNum as num, er.payment as payment," +
                "erd.sellPrice as price,date_format(er.operateDate,'%Y-%c-%d %h:%i:%s') as date,er.operatorName as operator,er.operatorId as operatorId," +
                "c.name as treeName,g.treeString as treeString "+
                "from t_sellRecordsDetail erd left join t_sellRecords er on erd.recordId = er.id " +
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
