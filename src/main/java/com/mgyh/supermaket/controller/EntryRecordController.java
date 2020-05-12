package com.mgyh.supermaket.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mgyh.supermaket.entity.EntryRecords;
import com.mgyh.supermaket.entity.EntryRecords;
import com.mgyh.supermaket.entity.EntryRecordsDetail;
import com.mgyh.supermaket.service.EntryRecordService;
import com.mgyh.supermaket.util.ExcelUtil;
import com.mgyh.supermaket.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
public class EntryRecordController {
    @Autowired
    private EntryRecordService entryRecordService;

    @PostMapping("/goods/entryGood")
    @ResponseBody
    public Object sellGoods(@RequestBody JSONObject object){
        EntryRecords entryRecords = new EntryRecords();
        JSONObject entryRecord = object.getJSONObject("entryRecord");;
        List<EntryRecordsDetail> detailList = new ArrayList<>();
        EntryRecordsDetail detail;
        JSONArray goodsList = object.getJSONArray("goodsList");
        JSONObject good;
        for(int i = 0; i < goodsList.size(); i++){
            good = goodsList.getJSONObject(i);
            detail = new EntryRecordsDetail();
            detail.setGoodsCode(good.getString("code"));
            detail.setGoodsNum(good.getString("num"));
            detail.setGoodsName(good.getString("name"));
            detail.setPurchasePrice(good.getBigDecimal("purchasePrice"));
            detail.setEntryRecords(entryRecords);
            detailList.add(detail);
        }
        entryRecords.setDetailList(detailList);
        try {
            entryRecordService.saveRecord(entryRecords);
        } catch (Exception e) {
            return new Result();
        }
        return new Result();
    }
}
