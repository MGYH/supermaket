package com.mgyh.supermaket.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mgyh.supermaket.entity.SellRecords;
import com.mgyh.supermaket.entity.SellRecordsDetail;
import com.mgyh.supermaket.service.SellRecordService;
import com.mgyh.supermaket.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class SellRecordController {
    @Autowired
    private SellRecordService sellRecordService;

    @PostMapping("/goods/sellGood")
    @ResponseBody
    public Object sellGoods(@RequestBody JSONObject object){
        SellRecords sellRecords = new SellRecords();
        String authCode = object.getString("authCode");
        JSONObject user = object.getJSONObject("name");;
        JSONObject sellRecord = object.getJSONObject("sellRecord");;
        sellRecords.setTotalMoney(sellRecord.getBigDecimal("totalMoney"));
        sellRecords.setChanges(sellRecord.getBigDecimal("change"));
        sellRecords.setPaid(sellRecord.getBigDecimal("paid"));
        sellRecords.setPayment(sellRecord.getString("payment"));
        sellRecords.setOperatorId(user.getString("id"));
        sellRecords.setOperatorName(user.getString("name"));
        List<SellRecordsDetail> detailList = new ArrayList<>();
        SellRecordsDetail detail;
        JSONArray goodsList = object.getJSONArray("goodsList");
        JSONObject good;
        BigDecimal purchase = BigDecimal.valueOf(0.00);
        for(int i = 0; i < goodsList.size(); i++){
            good = goodsList.getJSONObject(i);
            detail = new SellRecordsDetail();
            purchase.add(good.getBigDecimal("purchasePrice").multiply(BigDecimal.valueOf(good.getInteger("num")))) ;
            detail.setGoodsCode(good.getString("code"));
            detail.setEntryCode(good.getString("entryCode"));
            detail.setGoodsNum(good.getInteger("num"));
            detail.setGoodsName(good.getString("name"));
            detail.setSellPrice(good.getBigDecimal("price"));
            detail.setPurchasePrice(good.getBigDecimal("purchasePrice"));
            detail.setSellRecords(sellRecords);
            detailList.add(detail);
        }
        sellRecords.setTotalPurchaseMoney(purchase);
        sellRecords.setDetailList(detailList);
        try {
            sellRecordService.saveRecord(sellRecords, authCode);
        } catch (Exception e) {
            return new Result();
        }
        return new Result();
    }

    @PostMapping("/sell/getPieChart")
    @ResponseBody
    public Object getPieChart(@RequestBody JSONObject object) throws ParseException {
        return new Result(sellRecordService.getPieChart(object.getJSONObject("form")));
    }

    @PostMapping("/sell/typeReport")
    @ResponseBody
    public Object typeReport(@RequestBody JSONObject object) throws ParseException {
        return new Result(sellRecordService.getReport(object));
    }

    @PostMapping("/sell/getBarChart")
    @ResponseBody
    public Object getBarChart(@RequestBody JSONObject object) throws ParseException {
        return new Result(sellRecordService.getBarChartData(object.getJSONObject("form")));
    }

    @PostMapping("/sell/findAll")
    @ResponseBody
    public Object findAll(@RequestBody JSONObject object,int pageNo,int pageSize) throws ParseException {
        return new Result(sellRecordService.getSellGoodsList(object,pageNo,pageSize));
    }
}
