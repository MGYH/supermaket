package com.mgyh.supermaket.service;

import com.alibaba.fastjson.JSONObject;
import com.mgyh.supermaket.config.PayService;
import com.mgyh.supermaket.entity.SellRecords;
import com.mgyh.supermaket.entity.SellRecordsDetail;
import com.mgyh.supermaket.repository.SellRecordsDetailRepository;
import com.mgyh.supermaket.repository.SellRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SellRecordService {

    @Autowired
    private SellRecordsDetailRepository sellRecordsDetailRepository;

    @Autowired
    private SellRecordsRepository sellRecordsRepository;

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
}
