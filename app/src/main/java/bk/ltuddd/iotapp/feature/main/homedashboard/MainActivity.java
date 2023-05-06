package bk.ltuddd.iotapp.feature.main.homedashboard;

import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import bk.ltuddd.iotapp.core.base.BaseActivity;
import bk.ltuddd.iotapp.databinding.ActivityMainBinding;
import bk.ltuddd.iotapp.feature.auth.ui.LoginActivity;
import bk.ltuddd.iotapp.feature.main.viewmodel.MainViewModel;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    @Override
    protected ActivityMainBinding getViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<MainViewModel> getViewModelClass() {
        return MainViewModel.class;
    }

    @Override
    public void onCommonViewLoaded() {
        onLoading(false);

    }

    @Override
    public void addViewListener() {
        binding.btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            openActivity(LoginActivity.class);
        });

    }
}