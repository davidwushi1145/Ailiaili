package com.bilibili.dao.domain;

/**
 * 统一返回类
 * @param <T>
 */
public class JsonResponse<T> {

    private String code;
    private String msg;
    private  T data;

    public JsonResponse(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public JsonResponse(T data){
        this.data = data;
        msg = "成功";
        code = "200";
    }

    public static JsonResponse<String> success(){
        return new JsonResponse<String>(null);
    }

    public static JsonResponse<String> success(String data){
        return new JsonResponse<String>(data);
    }

    public static JsonResponse<String> fail(){
        return new JsonResponse<>("400","失败");
    }

    public static JsonResponse<String> fail(String code,String msg){
        return new JsonResponse<>(code, msg);
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
