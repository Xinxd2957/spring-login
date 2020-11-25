package com.example.springDemo.common.utils;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.lang.reflect.Field;

public class OkHttpClientRequest {

    private static OkHttpClient client = new OkHttpClient();

    public static Response get(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response;
    }


    /*
     * post请求 get不一样的地方就是传参数不一样，post请求需要把参数封装到RequestBody对象，
     * 调用Request对象的post方法把RequestBody传入进去
     */
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    public static Response post(String url, String json) throws IOException {
        Response response = postRespone(url, json);
        //    String result = response.body().string();
        System.out.println(json);
        return response;
    }


    public static Response postRespone(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    public static Response put(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        Response response = client.newCall(request).execute();
        //   String result = response.body().string();
        System.out.println(json);
        return response;
    }


    public static Response del(String url, String json) throws IOException {
        RequestBody body =RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .delete(body)
                .build();
        Response response = client.newCall(request).execute();
        //   String result = response.body().string();
        return response;
    }


    public static Response send(String url, Object json, RequestMethod sendType) throws IOException {


        if (sendType == RequestMethod.GET) {
            String parame = getParams(json);
            //  System.err.println(url + "?" + parame);
            Response result = get(url + "?" + parame);
            return result;
        } else if (sendType == RequestMethod.POST) {

            JSONObject data = setReflect(json);
            Response result = post(url, data.toJSONString());
            return result;
        } else if (sendType == RequestMethod.PUT) {

            JSONObject data = setReflect(json);
            Response result = put(url, data.toJSONString());
            return result;
        } else if (sendType == RequestMethod.DELETE) {

            JSONObject data = setReflect(json);
            Response result = del(url, data.toJSONString());
            return result;
        }


        return null;
    }

    public static Response request(String url, String json, RequestMethod method) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .method(String.valueOf(method), body)
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    public static JSONObject setReflect(Object model) {
        JSONObject requestMap = new JSONObject();

        for (Field field : model.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if ("".equals(field.get(model))) {
                    continue;
                }
                if (field.get(model) == null) {
                    continue;
                }
                requestMap.put(field.getName() + "", field.get(model) + "");
                System.out.println(field.getName() + ":" + field.get(model));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return requestMap;
    }


    /**
     * @param clazz 参数实体类
     * @return String
     * @Title: getPostParams
     * @Description: 将实体类clazz的属性转换为url参数
     */
    private static String getParams(Object clazz) {
        // 遍历属性类、属性值
        Field[] fields = clazz.getClass().getDeclaredFields();

        StringBuilder requestURL = new StringBuilder();
        try {
            boolean flag = true;
            String property, value;
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                // 允许访问私有变量
                field.setAccessible(true);

                // 属性名
                property = field.getName();
                // 属性值
                if (field.get(clazz) == null) {
                    continue;
                }
                value = field.get(clazz).toString();
                System.err.println(value);
                String params = property + "=" + value;
                if (flag) {
                    requestURL.append(params);
                    flag = false;
                } else {
                    requestURL.append("&" + params);
                }
            }
        } catch (Exception e) {
            System.err.println("URL参数为：" + clazz.toString());
        }
        return requestURL.toString();
    }


//另一种post

    /*
     * post请求 get不一样的地方就是传参数不一样，post请求需要把参数封装到RequestBody对象，
     * 调用Request对象的post方法把RequestBody传入进去
     */
    private static final MediaType JSONFORM = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");


    public static Response postForm(String url, String json) throws IOException {
        Response response = postResponeForm(url, json);
        //    String result = response.body().string();
        System.out.println(json);
        return response;
    }


    public static Response postResponeForm(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSONFORM, json);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }
}
