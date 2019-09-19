package com.xz.sm.contract;

import java.util.Map;

import okhttp3.Callback;

public interface Contract {

    interface Model {
        void get_Asyn(String url, Callback callback);

        void post_Asyn(String url, Map<String, String> parmars, Callback callback);

        void download_Asyn(String url, String locationPath, OnLoadCompleteListener listener);

        interface OnLoadCompleteListener {

            void successd(String data);

            void failed(Exception e);
        }


    }

    interface View {
        void sToast(String text);//短显示

        void lToast(String text);//长显示

        void showLoading(String msg);//显示等待框

        void dissmissLoading(String msg);//隐藏等待框

    }

    interface Presenter {
    }


}
