package com.cotton.abmallback.web.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

/**
 * FastJsonHttpDateConverter
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/19
 */
@Configuration
public class FastJsonHttpDateConverter  extends FastJsonHttpMessageConverter {

    private static SerializeConfig mapping = new SerializeConfig();

    static {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        mapping.put(Date.class, new SimpleDateFormatSerializer(dateFormat));
    }

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        OutputStream out = outputMessage.getBody();
        String text = JSON.toJSONString(obj, mapping, this.getFeatures());
        byte[] bytes = text.getBytes(this.getCharset());
        out.write(bytes);
    }

}
