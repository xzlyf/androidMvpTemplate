package com.xz.sm.activity.model;

import com.xz.sm.contract.Contract;
import com.xz.sm.utils.IOStreamUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Model implements Contract.Model {
//    private final long cacheSize = 1024 * 1024 * 20;//缓存文件限制20m
//    private String cachedirectory = "/caches";  //设置缓存文件路径
//    private Cache cache = new Cache(new File(cachedirectory), cacheSize);
    private OkHttpClient client;

    /**
     * 异步get请求
     *
     * @param url
     * @param callback
     */
    @Override
    public void get_Asyn(String url, Callback callback) {
        client = new OkHttpClient();
        client.newBuilder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS);
        Request.Builder requestBuilder = new Request.Builder()
                .url(url);
        requestBuilder.method("GET", null);
        Call call = client.newCall(requestBuilder.build());
        call.enqueue(callback);

    }

    /**
     * 异步post请求
     *
     * @param url
     * @param parmars
     * @param callback
     */
    @Override
    public void post_Asyn(String url, Map<String, String> parmars, Callback callback) {

        client = new OkHttpClient();
        client.newBuilder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS);
        FormBody.Builder formbody = new FormBody.Builder(Charset.forName("UTF-8"));
        for (Map.Entry<String, String> entry : parmars.entrySet()) {
            formbody.add(entry.getKey(), entry.getValue());
        }

        Request.Builder requestBuilder = new Request.Builder()
                .url(url);
        requestBuilder.method("POST", formbody.build());
        Call call = client.newCall(requestBuilder.build());
        call.enqueue(callback);
    }

    /**
     * 异步下载
     *
     * @param url          下载地址
     * @param locationPath 下载存储位置
     * @param listener
     */
    @Override
    public void download_Asyn(String url, final String locationPath, final OnLoadCompleteListener listener) {
        client = new OkHttpClient();
        client.newBuilder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS);
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                listener.failed(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                FileOutputStream fileOutputStream = null;

                try {
                    fileOutputStream = new FileOutputStream(new File(locationPath));
                    byte[] buffer = new byte[2048];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, len);
                    }
                    fileOutputStream.flush();

                } catch (Exception e) {
                    listener.failed(e);
                    e.printStackTrace();
                } finally {
                    IOStreamUtil.close(fileOutputStream, inputStream);
                }

                listener.successd(locationPath);
            }
        });
    }


}
