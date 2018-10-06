package com.cotton.base.third;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * OSSService
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/29
 */
@Component
public class OssService {

    private Logger logger = LoggerFactory.getLogger(OssService.class);

    @Value("oss.accessKeyId")
    private String accessKeyId = "oss.accessKeyId";
    @Value("oss.accessKeySecret")
    private String accessKeySecret = "oss.accessKeySecret";

    @Value("oss.endpoint")
    private String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    @Value("oss.bucketName")
    private String bucketName = "<YourBucketName>";

    private Map<String, String> getInfo(String dir){

        String host = "http://" + bucketName + "." + endpoint;
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConditions = new PolicyConditions();
            policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = client.generatePostPolicy(expiration, policyConditions);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);

            Map<String, String> respMap = new LinkedHashMap<>(10);
            respMap.put("accessId", accessKeyId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));

            return respMap;

        }catch (Exception e){

            logger.error("读取OSS参数错误",e);
        }
        return null;
    }
}
