package com.example.okhttpdemo.utils;

import com.example.okhttpdemo.exception.BusinessException;
import lombok.Cleanup;
import lombok.SneakyThrows;
import okhttp3.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author mick
 * @date 2018/8/15 19:13
 */
public class OkHttpUtils {

    private final static OkHttpClient CLIENT = new OkHttpClient.Builder()
            .connectionPool(new ConnectionPool(20, 5, TimeUnit.MINUTES))
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .build();

    /**
     *
     * 通用执行方法(GET, POST, PUT, DELETE, PATCH)-HTTP/HTTPS
     * @param param 请求参数
     * @author mick
     * @date 2018/8/15 19:11
     * @return String
     */
    @SneakyThrows
    public static String execute(OkHttpParam param){
        //-设置默认参数
        param.setRequestCharset(Optional.ofNullable(param.getRequestCharset()).orElse(ConstantUtils.DEFAULT_CHARSET));
        param.setResponseCharset(Optional.ofNullable(param.getResponseCharset()).orElse(ConstantUtils.DEFAULT_CHARSET));
        param.setMediaType(Optional.ofNullable(param.getMediaType()).orElse(ConstantUtils.APPLICATION_JSON));
        String method = Arrays.stream(new String[]{ConstantUtils.GET, ConstantUtils.POST, ConstantUtils.PUT, ConstantUtils.DELETE, ConstantUtils.PATCH})
                .filter(s1 -> s1.equals(param.getMethod().toUpperCase()))
                .findFirst().orElseThrow(() -> new BusinessException("请求方法不存在"));

        String url = param.getUrl();
        Request.Builder builder = new Request.Builder();
        //-设置查询参数
        if (Assert.isMapNotEmpty(param.getQueryMap())){
            String queryParams = param.getQueryMap().entrySet().stream()
                    .map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue()))
                    .collect(Collectors.joining("&"));
            url = String.format("%s%s%s", url, url.contains("?") ? "&" : "?", queryParams);
        }
        //-设置header参数
        if (Assert.isMapNotEmpty(param.getHeaderMap())){
            param.getHeaderMap().forEach(builder::addHeader);
        }
        //-设置表单参数
        if (Assert.isMapNotEmpty(param.getFormMap())){
            param.setData(param.getFormMap().entrySet().stream()
                    .map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue()))
                    .collect(Collectors.joining("&")));
        }
        builder.url(url);
        String mediaType = String.format("%s;charset=%s", param.getMediaType(), param.getRequestCharset());
        if (ConstantUtils.GET.equals(method)){
            builder.get();
        }else {
            RequestBody requestBody = RequestBody.create(MediaType.parse(mediaType), param.getData());
            builder.method(method, requestBody);
        }
        //发起调用请求
        @Cleanup Response response = CLIENT.newCall(builder.build()).execute();
        if (response.isSuccessful()){
            return response.body().string();
        }
        return null;
    }

}