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


    @GetMapping("/goods/getGoodsByCode")
    @ResponseBody
    public Object getGoodsByCode(@RequestParam(value = "goodCode")String code){
        return new Result(goodsService.getGoodsByCode(code));
    }


    @PostMapping("/goods/findAll")
    @ResponseBody
    public Object findAll(@RequestBody JSONObject object,int pageNo,int pageSize){
        return new Result(goodsService.getGoodsList(object,pageNo,pageSize));
    }

    @PostMapping("/goods/save")
    @ResponseBody
    public Object save(@RequestBody JSONObject object){
        goodsService.save(object.getObject("good",Goods.class));
        return new Result();
    }


    @PostMapping("/goods/getPieChart")
    @ResponseBody
    public Object getPieChart(@RequestBody JSONObject object){
        System.out.println(object.toJSONString());
        return new Result(goodsService.getPieChart(object.getJSONObject("from")));
    }
}
