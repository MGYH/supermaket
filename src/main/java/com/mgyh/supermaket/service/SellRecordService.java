package com.mgyh.supermaket.service;

import com.alibaba.fastjson.JSON;
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
import java.util.Date;


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

    @Autowired
    private EntryRecordService entryRecordService;



    @Transactional(rollbackFor = Exception.class)
    public void saveRecord(SellRecords sellRecords, String authCode) throws Exception {
        SellRecords result = sellRecordsRepository.save(sellRecords);
        for(SellRecordsDetail sellRecordsDetail : sellRecords.getDetailList()){
            goodsService.sellGoods(sellRecordsDetail.getGoodsCode(),sellRecordsDetail.getGoodsNum());
            sellRecordsDetailRepository.save(sellRecordsDetail);
            entryRecordService.sellGoods(sellRecordsDetail.getEntryCode(),sellRecordsDetail.getGoodsNum());
        }
        if(sellRecords.getPayment().equalsIgnoreCase("支付宝")){
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
    public JSONObject getSellGoodsList(JSONObject object) throws ParseException {
        return searchRepository.sqlSearch(getSellGoodsListSql(object),object);
    }
    public String getSellGoodsListSql(JSONObject object) throws ParseException {
        String sql = "select er.id as id,erd.goodsCode as code,erd.goodsName as name, erd.goodsNum as num, er.payment as payment," +
                "erd.sellPrice as price,date_format(er.operateDate,'%Y-%c-%d %h:%i:%s') as date,er.operatorName as operator,er.operatorId as operatorId," +
                "c.name as treeName,g.treeString as treeString,erd.purchasePrice as purchasePrice, erd.entryCode as entryCode,er.totalMoney as totalMoney," +
                " er.changes as changes, er.paid as paid "+
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
    public JSONObject getReport(JSONObject object) throws ParseException {
        JSONObject search = new JSONObject();
        String sql = "select date_format(s.operateDate, :format) as time,sum(srd.sellPrice) totalMoney,sum(srd.sellPrice)-sum(srd.purchasePrice) profit,sum(s.paid) as paid,sum(s.changes) as changes" +
                " from t_sellrecords s left join t_sellrecordsdetail srd on s.id = srd.recordId" +
                " where s.operateDate >= :startDate and s.operateDate <= :endDate " +
                " group by date_format(s.operateDate, :format) order by date_format(s.operateDate, :format)";
        return getJsonObject(object, search, sql);
    }

    public JSONObject getBarChartData(JSONObject object) throws ParseException {
        JSONObject search = new JSONObject();
        String sql = "select date_format(sr.operateDate, :format) as name,sum(srd.sellPrice) value1,sum(srd.sellPrice)-sum(srd.purchasePrice) value2 from t_sellrecords sr" +
                " left join t_sellrecordsdetail srd on sr.id = srd.recordId" +
                " where sr.operateDate >= :startDate and sr.operateDate <= :endDate " +
                " group by date_format(sr.operateDate, :format) order by date_format(sr.operateDate, :format)";
        return getJsonObject(object, search, sql);
    }

    private JSONObject getJsonObject(JSONObject object, JSONObject search, String sql) throws ParseException {
        String dateStr = object.getString("operateDate");
        if(object.getString("type").equals("month")){
            search.put("format","%Y-%m-%d");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            search.put("startDate",format.parse(dateStr+"-1"));
            search.put("endDate",format.parse(dateStr+"-31"));
        }else{
            search.put("format","%Y-%m");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            search.put("startDate",format.parse(dateStr+"-1-1"));
            search.put("endDate",format.parse(dateStr+"-12-31"));
        }
        return searchRepository.sqlSearch(sql,search);
    }
}
