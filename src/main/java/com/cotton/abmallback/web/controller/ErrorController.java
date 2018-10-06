package com.cotton.abmallback.web.controller;

import com.cotton.base.common.RestResponse;
import com.cotton.base.common.RestResponseFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ErrorController
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/22
 */
@Controller
public class ErrorController {

    @RequestMapping("/404")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RestResponse notFound() {
        return RestResponse.getFailedResponse(404,"接口不存在");
    }


    @RequestMapping("/500")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResponse fiveoo() {
        return RestResponse.getFailedResponse(500,"系统异常");
    }
}
