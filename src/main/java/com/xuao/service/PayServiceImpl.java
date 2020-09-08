package com.xuao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.xuao.alipay.Alipay;
import com.xuao.alipay.AlipayBean;

@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private Alipay alipay;
    
    @Override
    public String aliPay(AlipayBean alipayBean) throws AlipayApiException {
        return alipay.pay(alipayBean);
    }

}