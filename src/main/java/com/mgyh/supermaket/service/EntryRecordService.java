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
            goodsService.entryGoods(entryRecordsDetail.getGoodsCode(),entryRecordsDetail.getGoodsNum());
            entryRecordsDetailRepository.save(entryRecordsDetail);
        }
    }

    public JSONObject getEntryReport(JSONObject object){
        String sql = "select new map(c.goodsCode as name, sum(c.goodsNum) as value) from EntryRecordsDetail c where 1=1 group by c.goodsCode";
        return searchRepository.commonSearch(sql,new JSONObject());
    }
}
