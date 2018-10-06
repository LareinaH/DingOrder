package com.cotton.abmallback.third.alibaba.alipay;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * AlipayService
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/26
 */
@Service
public class AlipayServiceImpl implements AlipayService{

    private Logger logger = LoggerFactory.getLogger(AlipayServiceImpl.class);

    @Value("${alipay.appId}")
    private String appId;

    @Value("${alipay.merchantId}")
    private String merchantId;

    //private String appId = "2018062960528027";
    //private String merchantId = "2018062960528027";

    @Value("${alipay.secret}")
    private String secret;

    @Value("${alipay.payNotifyUrl}")
    private String payNotifyUrl;

    @Value("${alipay.refundNotifyUrl}")
    private String refundNotifyUrl;

    @Value("${alipay.webReturnUrl}")
    private String webReturnUrl;

    @Value("${alipay.wapReturnUrl}")
    private String wapReturnUrl;

    AlipayClient alipayClient;


    private String appPrikey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDqSizbURHRoDmC\n"
            + "ma0vgqFfyRxXrM+bs1z+erGd7PvGLURWWS3hiGol0k69CiL1vza17SzIi4kLDC4V\n"
            + "4K9ApwAsyD9/tHq3Eg+YvY2jkZuBV98lJt1wJDKJkF7HcKopq73wdV1RpK5oO8RZ\n"
            + "IsUZtrW5KJ9/p/bqlFIRBsolxaUBZPb6WgKaN+1t1hO/c4Fuk0XhhmFQYYN8v79+\n"
            + "+Iik+rKF9huzPnxG8vruHq6hbSpVhKI1YOnPIojeFfgQiPP3AY6qf0f0ZqwNO1id\n"
            + "4S0bzMra4elDl6RibbxTQpH95PTm3xz183Cg/elC8GoiQl+vsRmYFcssnIJ9H+P+\n"
            + "ocsbGuIxAgMBAAECggEAdbf5W+Ua/+nym4VduE55iOhCvmbNFZ2ErzS0w/YL92JN\n"
            + "QNmkLPre4swN1fwe3r1J3xUheE5r+EoRadqwE9sVQmsHRMJJGm0Pux4a6cHCkbW2\n"
            + "IDOr9amqXuZUSP958GAjmotN7TTCkQreuC65PBdKrZMUuQ6LCQinR3jS3zG0m8LJ\n"
            + "8pAqae6jMcRdtGxEXCX8a/XdGK1U5ANOuX34D5Hg+S4zHQiyUA4mIwui9vVFAnZh\n"
            + "HoBuxJROeoGoCKj64wBqufNSfBQjWs26PNU4T4zJfygiE8yyN1MdaZ6envOwe+XN\n"
            + "nRNYEsTPa3408G2hE7y8eHJcvde3JtThqOKac/LH8QKBgQD+Lw5/qanW253gAsxS\n"
            + "o7Q1ddjqwmib/pFlAVzS4P+IeRbQ/H2hzE7mnDArjW/w8pdXNo6CTRJg/1VxXMC2\n"
            + "XcUTRhU04YOPcUOowfVzU9sNk/Ifr2b00aYmerOmrAXHUmUmF8IL0wMsPpKmAW6T\n"
            + "rACEs3OvihK3nlmhQioZJwuUZQKBgQDr9rqnMMM5e3+o6bCXZHkrccszCdBHdMnF\n"
            + "h2AJt085O6gZjCDZCvGmXdf7ZXZHMBpxCzO6jZWEAzpgW2Fn2iBBcJKvH82exlwz\n"
            + "WWj39jFfJXKmgm94j+wAkNsLFsTlw6ITZI55bc/ue4qc2vSLz7ZVPlOf+atIwVc3\n"
            + "bSjsW3673QKBgQCq28y+KbjdkVCFJLxdjGb1TJsb6sRQn4TyRUE1C0MZZHPe1OpK\n"
            + "GUCsKS8EB5XIe/kZCbYvhkklZFz1z6hGra9sbj6RBknd4P/e70njVOm5LcqiW9A0\n"
            + "Hry1vuMF1TopKoyNV4j7U8MdOY5wAiRnJUZP7SSFSaWdQdbz27ran3FcNQKBgQCB\n"
            + "SgZJt3EwnNd88NaejSHLSSWCiJ0Dmh04Sw23JSaWgHaB0QLqiZGQi5jdGWHubY//\n"
            + "YpjsXcmPtMkWpNtBMQY9dPYaWH2swpkgVZwrSU0SCg3A6HU1hP5V7QjoEYi/MCst\n"
            + "Hwrlw+KLlEuF2H7n4F7SZD0jyYQtcCpep0QmBZfyTQKBgQDJwvekrl3bEc5c7ZWw\n"
            + "EMrRynfYMmW7CuKjOikEYSYCM7aZhsJ31BSIOLBcT8ARr1ob8DAGbIV7FSfC697E\n"
            + "7BNFKW5hJ82RLe2qZn78rvYkebJ8p+22JEx4eGImHGFPaX7qbCClp6oXDSwLKFqm\n"
            + "I5bfaHvVreBm+RNCq+KevrkNxg==";

    private String appPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6kos21ER0aA5gpmtL4Kh\n"
            + "X8kcV6zPm7Nc/nqxnez7xi1EVlkt4YhqJdJOvQoi9b82te0syIuJCwwuFeCvQKcA\n"
            + "LMg/f7R6txIPmL2No5GbgVffJSbdcCQyiZBex3CqKau98HVdUaSuaDvEWSLFGba1\n"
            + "uSiff6f26pRSEQbKJcWlAWT2+loCmjftbdYTv3OBbpNF4YZhUGGDfL+/fviIpPqy\n"
            + "hfYbsz58RvL67h6uoW0qVYSiNWDpzyKI3hX4EIjz9wGOqn9H9GasDTtYneEtG8zK\n"
            + "2uHpQ5ekYm28U0KR/eT05t8c9fNwoP3pQvBqIkJfr7EZmBXLLJyCfR/j/qHLGxri\n"
            + "MQIDAQAB";

    private String payPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgam4AlG" +
            "gkuezeCjdmwWn4x6Dm3RXQS+xEUq3drkIFrzhMDuZUDnqGB/1dh4xRP6QIKKbobXywRa7uR/h" +
            "WPx5taw/Zco1gFypYa9HkbC8Ts3zzJSliGY6PL0q0yAaxVt6e2iGKW1MBMjrBhWGGAdjykiorBr" +
            "aogtu1Wwc47zwl92Pr/fRhZXfos9fnRa+iv/hdRwRm01xUdM5N+kBlIQOoCh20huM3LkWirjeIPB" +
            "jNETDbz4kVsNigY8RkaGSGIYuV/p/7djVROC5ZtpXQp0EYUeNgiIwcp/qD7cRZItcizsWe4uWZ1Wa" +
            "HYGI9yZoAZaTToCP90aa9YmJzOgwGR7PjQIDAQAB";


    /*private String appPrikey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC//I9c49mjSoyXy8TYMDLWGBTQWH4uVf+lFK82lP2AVotpb+cOJIsbKZwqm+mwrRWQ9LlqL7FAXQHaBEXMGUntG6kSKIOJDIJvWIwi4mY1SHhkPZxFIfYOeVq0bZQmEidrT4982x6Av/dSxmG1oso6Wer8YIlpaHqCHRJMPmBvo4/q2tUgt3Ferd+0fBZTwfCXSrY8byO8U/pf1Ln+p9QH6uwxrZIvuvnvqzp/xtdyve09nXHJPG6M7Kc4lrL4mDFhQOHuz3NSjeoQPRp9HAdYIy3GKlTJgsaV8Vw0ox0uX4oGMngdgXoKPQm4Kq3GYmoGQY9ScuMUYTjk6CKfIh8hAgMBAAECggEAWL6QLmoMclTFhG1mwmAVP63GAUDGoviRwWc8Tsi0HZzjuItHjNwLiTTEwVVswoRfka/t/U2qa4wSjQqokN2ntHiywHgFxVYI/Rs7O+zXPg4PyJZKJBK8wJgL+5cjgi2mFw6hzx8ijHUwCA03oVRskmh6HMsq+ZSX/IkfqiqzPSbbLNVahrQYu8h84JS8j2YGbyCGJu7dyRRg3aCfqlayiofUowREBqsXDn4IhsYOFHyDCzRcAacIVy7dLry9cY10fnfvOARTX/jeEH6qD09FSdp2q+NmOS+Bz3yJMgovq2cxZEVQ9lFzJHn/Zhyj0HOiEzROK9a9jvuxv72cE+cSuQKBgQDdtN6GLqEtBKILOx6IsuqoywXbc3A2Ix95jaKxNfjAPUfBQfm5H6BAUCQNeDf2J/FJ1s5FD9/W/fPF9EDvABpxCpzOa3bO+gSm00JYOhAbz+JKF1ya6E3Jd5UAJXGfbCnxB0UUa40zB7hKoHqr7X6aL9IzjhSgVQMxXfcJNpGHgwKBgQDdrtcl/nQJ6LKR7PjHlK8kVG65fqO5W5l0HIfQI/JJCU2je/k3xLdiFE4TWZuVGQVUDbqc4RZVoC8lZfxS3dBUUxaMsaRdH1L56PVUM9uyQxRicAhWg3nix0RNvUMnwLKomZl/yamymjrBOJxEdj5DAtkDGl6wN8bPRLH1YStZiwKBgQDRYsuygIF6KeD8qGYLBqb2qV+rxZeZmYxVWX3ozadTr8x/6dZ2jzbi5o1WJ0767PhGG1gOA5MM0iUTtL5Kupc+YZ58mSPJEHmqMOV8u8GQ8sGt5ehw6KrmdkjDrZlkzlvCJnKIeZcONeFIoc2ZaKBj8HwecrZqQj/UtQDls6K4DwKBgCa9kFHOdv7oUJSZ4hwWysjBjJwUGo6hCF83QTDJp2TGIHBKkRIC6b1VKRPOnBbhSYIX6B3UZC/Qj4yslvzwUKD76DvsK3ouqDSOVplBespbWKufQoXD8kAEbNZdFehTj6VROncPif1xYIU13HFGbLkPPVeCezu2c6LzMVpdgdLjAoGBAKxb43ycnWGHUXQH2PcKOc8gpwwau3F8QhDgSL0fwvPjLqOujlLvHUEf47h4i4TjusL+WUf4nIQAlfbNHTCTRebxTjYQm4Nd6GmjhDf1TjqJ367YepXeaCPHRzsEHtURmWnNv2VUlGryamzVnRLrA7KvmLrW6Y/7SNZt81kQwH3W";
    private String appPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAv/yPXOPZo0qMl8vE2DAy1hgU0Fh+LlX/pRSvNpT9gFaLaW/nDiSLGymcKpvpsK0VkPS5ai+xQF0B2gRFzBlJ7RupEiiDiQyCb1iMIuJmNUh4ZD2cRSH2DnlatG2UJhIna0+PfNsegL/3UsZhtaLKOlnq/GCJaWh6gh0STD5gb6OP6trVILdxXq3ftHwWU8Hwl0q2PG8jvFP6X9S5/qfUB+rsMa2SL7r576s6f8bXcr3tPZ1xyTxujOynOJay+JgxYUDh7s9zUo3qED0afRwHWCMtxipUyYLGlfFcNKMdLl+KBjJ4HYF6Cj0JuCqtxmJqBkGPUnLjFGE45OginyIfIQIDAQAB";
    private String payPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq7m/TY605qfRhSGgEUyEZ0mcPCd5+xOdY0unG2gS6o+7hd9skJ0A7DYN5KKsWjr2anqUx9Nq6iydMOOhm2zwGUa3E3FtqTlRj19Drwr/5CrTgUsY52FiY+UVx3sv6dhStSKCI+1nN7HeDuzgRPcRE90/qLtPuGxAgQBzR+Kf8zTSZ5taH85W7RSpanq0mASckMwEVR8RsvPLb+th+4zRVMTMl2O2ysbktd64RLU4inslSih5S0TmUlFdDCDypPaN1UOcBC0diKesvLfBZXXaqpuNgP+ApiF5aarm7vKS6emNN9+LU6mvwepMPwyrLFCq80GFZULtNIaUgwoJCdOv6QIDAQAB";

*/
    @PostConstruct
    public void initAlipay(){
        alipayClient = new DefaultAlipayClient(
                "https://openapi.alipay.com/gateway.do", appId,appPrikey,
                "json", "utf-8", appPubKey, "RSA2");


        System.err.println(alipayClient);
    }

    /**
     * 验证
     */
    @Override
    public Boolean notifyVerify(Map<String, String> params){
        try {
            return AlipaySignature.rsaCheckV1(params, payPubKey,
                    AlipayConstants.CHARSET_UTF8,"RSA2");
        } catch (AlipayApiException e) {
            logger.error("支付宝回调参数校验错误",e);
        }
        return false;
    }


    /**
     * 支付宝支付
     * @param tradeNo
     * @param amount
     * @return
     */
    @Override
    public Map<String, Object> payWithAlipay(String tradeNo, BigDecimal amount) {
        //实例化客户端

        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("这事商品");
        model.setSubject("商品描述");
        model.setOutTradeNo(tradeNo);
        model.setTimeoutExpress("30m");
        model.setTotalAmount(amount.toString());
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(payNotifyUrl);
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            logger.info("【支付宝APP支付】返回报文，response=" + JSON.toJSONString(response));
            Map<String, Object> payParam = new HashMap<String, Object>();
            payParam.put("body", response.getBody());
            return payParam;
        } catch (AlipayApiException e) {
            logger.warn("【支付宝APP支付】执行异常,result=" + JSON.toJSONString(e));
        }
        return null;
    }
}