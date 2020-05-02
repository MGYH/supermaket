package com.mgyh.supermaket.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mgyh.supermaket.entity.Goods;
import com.mgyh.supermaket.entity.SellRecords;
import com.mgyh.supermaket.entity.SellRecordsDetail;
import com.mgyh.supermaket.service.GoodsService;
import com.mgyh.supermaket.service.SellRecordService;
import com.mgyh.supermaket.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MGYH
 * @date 2020/3/22
 */

@RestController
@CrossOrigin
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private SellRecordService sellRecordService;

    @GetMapping("/goods/getGoodsByCode")
    @ResponseBody
    public Object getGoodsByCode(@RequestParam(value = "goodCode")String code){
        System.out.println("===========");
        return new Result(goodsService.getGoodsByCode(code));
    }

    @PostMapping("/goods/sellGood")
    @ResponseBody
    public Object sellGoods(@RequestBody JSONObject object){
        SellRecords sellRecords = new SellRecords();
        String authCode = object.getString("authCode");
        String payment = object.getString("payment");
        System.out.println(authCode+"=====================");
        JSONObject sellRecord = object.getJSONObject("sellRecord");;
        sellRecords.setTotalMoney(sellRecord.getString("totalMoney"));
        sellRecords.setChange(sellRecord.getString("change"));
        sellRecords.setPaid(sellRecord.getString("paid"));
        List<SellRecordsDetail> detailList = new ArrayList<>();
        SellRecordsDetail detail;
        JSONArray goodsList = object.getJSONArray("goodsList");
        JSONObject good;
        for(int i = 0; i < goodsList.size(); i++){
            good = goodsList.getJSONObject(i);
            detail = new SellRecordsDetail();
            detail.setGoodsCode(good.getString("code"));
            detail.setGoodsNum(good.getString("num"));
            detail.setSellPrice(good.getString("price"));
            detail.setSellRecords(sellRecords);
            detailList.add(detail);
        }
        sellRecords.setDetailList(detailList);
        try {
            sellRecordService.saveRecord(sellRecords, authCode);
        } catch (Exception e) {
            return new Result();
        }
        return new Result();
    }

    @PostMapping("/goods/findAll")
    @ResponseBody
    public Object findAll(@RequestBody JSONObject object){
        return new Result(goodsService.findAll());
    }

    @PostMapping("/goods/save")
    @ResponseBody
    public Object save(@RequestBody JSONObject object){
        System.out.println(object.toJSONString());
        goodsService.save(object.getObject("good",Goods.class));
        return new Result();
    }
}
