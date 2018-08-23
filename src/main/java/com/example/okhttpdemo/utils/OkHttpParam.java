package com.example.okhttpdemo.utils;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Map;

/**
 * @author mick
 * 2018/8/15
 */
@Builder
@Data
public class OkHttpParam {
    @NonNull
    private String url;
    @NonNull
    private String method;
    private String data;
    private String mediaType;
    private Map<String, String> queryMap;
    private Map<String, String> headerMap;
    private Map<String, String> formMap;
    private String requestCharset;
    private String responseCharset;
}
