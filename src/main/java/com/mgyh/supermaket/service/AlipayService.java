package com.mgyh.supermaket.service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.domain.GoodsDetail;
import com.alipay.api.response.AlipayTradePayResponse;
import com.mgyh.supermaket.config.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MGYH
 * @date 2020/3/29
 */
@Service
@Transactional
public class AlipayService {
//    @Autowired
//    public PayService service;
//    public void tradPay(String authCode,String id){
//        service.alipayTradePayService(authCode);
//    }
//    public void tradPay_old(String authCode,String id){
//        Map<String, Object> params = new HashMap<String, Object>();
//        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
//        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
//        String outTradeNo = "tradepay" + System.currentTimeMillis()
//                + (long) (Math.random() * 10000000L);
//        params.put("out_trade_no", outTradeNo);
//        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店消费”
//        String subject = "xxx品牌xxx门店当面付消费";
//        params.put("subject", subject);
//        // (必填) 订单总金额，单位为元，不能超过1亿元
//        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
//        String totalAmount = "1";
//        params.put("total_amount", totalAmount);
//        // (必填) 付款条码，用户支付宝钱包手机app点击“付款”产生的付款条码
//        params.put("auth_code", authCode);
//        // (可选，根据需要决定是否使用) 订单可打折金额，可以配合商家平台配置折扣活动，如果订单部分商品参与打折，可以将部分商品总价填写至此字段，默认全部商品可打折
//        // 如果该值未传入,但传入了【订单总金额】,【不可打折金额】 则该值默认为【订单总金额】- 【不可打折金额】
//        //        String discountableAmount = "1.00"; //
//        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
//        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
//        String undiscountableAmount = "0.0";
//        params.put("undiscountable_amount", "undiscountableAmount");
//        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
//        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
//        String sellerId = "";
//        params.put("seller_id", sellerId);
//        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品3件共20.00元"
//        String body = "购买商品3件共20.00元";
//        params.put("body", body);
//        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
//        String operatorId = "test_operator_id";
//        params.put("operator_id", operatorId);
//        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
//        String storeId = "test_store_id";
//        params.put("store_id", storeId);
//        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
//        String providerId = "2088100200300400500";
//
//        // 支付超时，线下扫码交易定义为5分钟
//        String timeoutExpress = "5m";
//        params.put("timeout_express", timeoutExpress);
//
//        // 商品明细列表，需填写购买商品详细信息，
//        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
//        // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
//        GoodsDetail goods1 = new GoodsDetail();
//        goods1.setGoodsId("1");
//        goods1.setBody("测试");
//        goods1.setGoodsName("测试物品");
//        goods1.setPrice("2");
//        goods1.setQuantity(1L);
//        // 创建好一个商品后添加至商品明细列表
//        goodsDetailList.add(goods1);
//
//        params.put("goods_detail", goodsDetailList);
//        String appAuthToken = "应用授权令牌";//根据真实值填写
//
//        Object obj = JSONObject.toJSON(params);
//
//        // 调用tradePay方法获取当面付应答
//        AlipayTradePayResponse result = (AlipayTradePayResponse) service.alipayTradePayService(obj);
//        System.out.println(result);
//        System.out.println("=======");
//
//    }
}
