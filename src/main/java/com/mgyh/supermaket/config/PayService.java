package com.mgyh.supermaket.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.BaseClient.Config;


import java.math.BigDecimal;
import java.util.*;


import com.alipay.easysdk.payment.common.models.AlipayTradeCreateResponse;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePayResponse;
import org.bouncycastle.math.ec.ECCurve;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class PayService {
    public static final Logger   logger = LoggerFactory.getLogger(PayService.class);

	public Object alipayTradePayService(String authCode, BigDecimal totalMoeny, String outTradeNo) throws Exception {
		// 1. 设置参数（全局只需设置一次）
		Factory.setOptions(getOptions());
		AlipayTradePayResponse response = Factory.Payment.FaceToFace().pay("我在测试", outTradeNo, String.valueOf(totalMoeny), authCode);
		// 3. 处理响应或异常
		if ("10000".equals(response.code)) {
			System.out.println("调用成功");
		} else {
			System.err.println("调用失败，原因：" + response.msg + "，" + response.subMsg);
		}
		return null;
	}

    public static AlipayClient getAlipayClient() {
		Locale locale = Locale.getDefault();
		ResourceBundle bundle = ResourceBundle.getBundle("zfbinfo",locale );
		// 网关
		String URL = bundle.getString("ALIPAY_GATEWAY_URL");
		// 商户APP_ID
		String APP_ID = bundle.getString("APP_ID");
		// 商户RSA 私钥
		String APP_public_KEY = bundle.getString("RSA2_PRIVATE_KEY");
		// 请求方式 json
		String FORMAT = bundle.getString("FORMAT");
		// 编码格式，目前只支持UTF-8
		String CHARSET = bundle.getString("CHARSET");
		// 支付宝公钥
		String ALIPAY_PUBLIC_KEY = bundle.getString("ALIPAY_RSA2_PUBLIC_KEY");
		// 签名方式
		String SIGN_TYPE = bundle.getString("SIGN_TYPE");
		return new DefaultAlipayClient(URL, APP_ID, APP_public_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
	}
	private static Config getOptions() {
		Locale locale = Locale.getDefault();
		ResourceBundle bundle = ResourceBundle.getBundle("zfbinfo",locale );
		Config config = new Config();
		config.protocol = "https";
		config.gatewayHost = bundle.getString("ALIPAY_GATEWAY_URL");
		config.signType = bundle.getString("SIGN_TYPE");

		config.appId = bundle.getString("APP_ID");

		// 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
		config.merchantPrivateKey = bundle.getString("RSA2_PRIVATE_KEY");

		//注：证书文件路径支持设置为文件系统中的路径或CLASS_PATH中的路径，优先从文件系统中加载，加载失败后会继续尝试从CLASS_PATH中加载
//		config.merchantCertPath = "";
//		config.alipayCertPath = "<-- 请填写您的支付宝公钥证书文件路径，例如：/foo/alipayCertPublicKey_RSA2.crt -->";
//		config.alipayRootCertPath = "<-- 请填写您的支付宝根证书文件路径，例如：/foo/alipayRootCert.crt -->";

		//注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
		 config.alipayPublicKey = bundle.getString("ALIPAY_RSA2_PUBLIC_KEY");
		return config;
	}
}