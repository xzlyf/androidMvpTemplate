package com.xz.sm.activity.presenter;

import com.xz.sm.activity.MainActivity;
import com.xz.sm.contract.Contract;
import com.xz.sm.activity.model.Model;

public class MainPresenter implements Contract.Presenter {
    private Model mModel;
    private MainActivity activity;

    public MainPresenter(MainActivity activity) {
        mModel = new Model();
        this.activity = activity;
    }


}
