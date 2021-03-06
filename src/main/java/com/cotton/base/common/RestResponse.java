package com.cotton.base.common;

import java.io.Serializable;

/**
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/10
 */

public class RestResponse<T> implements Serializable {

    private static final long serialVersionUID = 4843066638830850455L;

    private int code;
    private String message;
    private T data;

    //成功
    private static  int SUCCESS = 200;

    //未登录
    private static  int UNAUTHORIZED = 401;

    //内部异常
    private static  int INNER_ERROR = 500;


    private RestResponse() {
        this.code = SUCCESS;
    }


    public static<T> RestResponse<T> getSuccesseResponse(){

        return getSuccesseResponse(null);
    }

    public static<T> RestResponse<T> getSuccesseResponse(T data){
        RestResponse restResponse = new RestResponse();
        restResponse.code = SUCCESS;
        restResponse.data = data;
        return restResponse;
    }

    public static<T> RestResponse<T> getUnauthorizedFailedResponse(){

        return getFailedResponse(UNAUTHORIZED,"用户未登录",null);
    }

    public static<T> RestResponse<T> getFailedResponse(int code, String message){

        return getFailedResponse(code,message,null);
    }

    public static<T> RestResponse<T> getFailedResponse(int code, String message,T data){
        RestResponse restResponse = new RestResponse();
        restResponse.code = code;
        restResponse.message = message;
        restResponse.data = data;
        return restResponse;
    }


    public static<T> RestResponse<T> getSystemInnerErrorResponse(){

        return getSystemInnerErrorResponse(null);
    }

    public static<T> RestResponse<T> getSystemInnerErrorResponse(String message){
        RestResponse restResponse = new RestResponse();
        restResponse.code = INNER_ERROR;
        restResponse.message = "系统内部异常：" + message;
        return restResponse;
    }

    public boolean getSuccessed(){

        return code == SUCCESS;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RestResponse [code=" + code + ", message="
                + message + ", data=" + data + "]";
    }

}
