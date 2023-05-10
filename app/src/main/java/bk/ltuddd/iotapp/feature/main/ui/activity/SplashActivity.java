package bk.ltuddd.iotapp.feature.main.ui.activity;

import android.content.Intent;
import android.os.Handler;

import bk.ltuddd.iotapp.core.base.BaseActivity;
import bk.ltuddd.iotapp.feature.auth.ui.LoginActivity;
import bk.ltuddd.iotapp.databinding.ActivitySplashBinding;
import bk.ltuddd.iotapp.feature.main.viewmodel.MainViewModel;

public class SplashActivity extends BaseActivity<ActivitySplashBinding, MainViewModel> {

    @Override
    protected ActivitySplashBinding getViewBinding() {
        return ActivitySplashBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<MainViewModel> getViewModelClass() {
        return MainViewModel.class;
    }

    @Override
    public void onCommonViewLoaded() {
        delayFunction();
    }

    @Override
    public void addViewListener() {

    }

    private void delayFunction() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        }, 6000);
    }

}
