package com.xuao.alipay;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;

/**
 * 支付宝支付接口
 * 
 * @author Louis
 * @date Dec 12, 2018
 */
@Component
public class Alipay {

	/**
	 * 支付接口
	 * 
	 * @param alipayBean
	 * @return
	 * @throws AlipayApiException
	 */
	public String pay(AlipayBean alipayBean) throws AlipayApiException {
		String serverUrl = AlipayProperties.getGatewayUrl();
		String appId = AlipayProperties.getAppId();
		String privateKey = AlipayProperties.getPrivateKey();
		String format = "json";
		String charset = AlipayProperties.getCharset();
		String alipayPublicKey = AlipayProperties.getPublicKey();
		String signType = AlipayProperties.getSignType();
		String returnUrl = AlipayProperties.getReturnUrl();
		String notifyUrl = AlipayProperties.getNotifyUrl();
		AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey, format, charset,
				alipayPublicKey, signType);
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(returnUrl);
		alipayRequest.setNotifyUrl(notifyUrl);
		alipayRequest.setBizContent(JSON.toJSONString(alipayBean));
		String result = alipayClient.pageExecute(alipayRequest).getBody();
		return result;
	}
}
