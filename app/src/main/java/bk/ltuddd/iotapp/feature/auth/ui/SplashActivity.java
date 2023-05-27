package bk.ltuddd.iotapp.feature.auth.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import bk.ltuddd.iotapp.core.base.BaseActivity;
import bk.ltuddd.iotapp.feature.auth.ui.LoginActivity;
import bk.ltuddd.iotapp.databinding.ActivitySplashBinding;
import bk.ltuddd.iotapp.feature.auth.viewmodel.AuthViewModel;
import bk.ltuddd.iotapp.feature.main.ui.activity.MainActivity;
import bk.ltuddd.iotapp.feature.main.viewmodel.MainViewModel;
import bk.ltuddd.iotapp.utils.Constant;

public class SplashActivity extends BaseActivity<ActivitySplashBinding, AuthViewModel> {

    @Override
    protected ActivitySplashBinding getViewBinding() {
        return ActivitySplashBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<AuthViewModel> getViewModelClass() {
        return AuthViewModel.class;
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.isLogin.observe(this, isLogin -> {
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                if (isLogin) {
                    openActivity(MainActivity.class);
                } else {
                    openActivity(LoginActivity.class);
                }
                finish();
            }, 6000);
        });
    }

    @Override
    public void onCommonViewLoaded() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constant.SHARED_PREFERENCE_FILE_NAME,Context.MODE_PRIVATE);
        String uid = sharedPreferences.getString(Constant.KEY_UID_PREF, Constant.EMPTY_STRING);
        viewModel.checkIsLogin(uid);
    }

    @Override
    public void addViewListener() {

    }



}
