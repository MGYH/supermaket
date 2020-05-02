package com.mgyh.supermaket.controller;

import com.mgyh.supermaket.config.AlipayConfig;
import com.mgyh.supermaket.service.AlipayService;
import com.mgyh.supermaket.service.GoodsService;
import com.mgyh.supermaket.util.Result;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author MGYH
 * @date 2020/3/29
 */
@RestController
@CrossOrigin
public class PayController {
    @Autowired
    public AlipayConfig alipayConfig;
    @Autowired
    public AlipayService alipayService;
    @Autowired
    public GoodsService goodsService;



//    @GetMapping("/zfb/tradePay")
//    @ResponseBody
//    public Object trade_pay(@RequestParam(value = "authCode")String authCode){
//        System.out.println(authCode+"================");
//        alipayService.tradPay(authCode,"1");
//        return new Result();
//    }
}
