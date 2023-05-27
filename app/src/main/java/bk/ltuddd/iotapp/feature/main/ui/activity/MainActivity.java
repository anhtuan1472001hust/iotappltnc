package bk.ltuddd.iotapp.feature.main.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import bk.ltuddd.iotapp.R;
import bk.ltuddd.iotapp.core.base.BaseActivity;
import bk.ltuddd.iotapp.data.model.DeviceModel;
import bk.ltuddd.iotapp.databinding.ActivityMainBinding;
import bk.ltuddd.iotapp.feature.auth.ui.LoginActivity;
import bk.ltuddd.iotapp.feature.main.ui.adapter.MainAdapter;
import bk.ltuddd.iotapp.feature.main.ui.fragment.UserInfoFragment;
import bk.ltuddd.iotapp.feature.main.viewmodel.MainViewModel;
import bk.ltuddd.iotapp.utils.Constant;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    private MainAdapter mainAdapter = new MainAdapter();
    private GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);

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
        viewModel.getListDevice();
        binding.rcvDevice.setAdapter(mainAdapter);
        binding.rcvDevice.setLayoutManager(gridLayoutManager);
        mainAdapter.setOnBtnStateLampClick((serial, state) -> {
            viewModel.updateLampState(serial,state);
        });
        onLoading(false);
        onClickNavigationDrawer();
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();

        viewModel.listDeviceModel().observe(this, listDevice -> {
            mainAdapter.submitList(listDevice);
        });


    }

    @Override
    public void addViewListener() {
        binding.navHeaderMain.tvLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            binding.dlLayout.closeDrawer(GravityCompat.START);
            openActivity(LoginActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK,Intent.FLAG_ACTIVITY_NEW_TASK,Intent.FLAG_ACTIVITY_CLEAR_TASK);
        });
        binding.navHeaderMain.ivEmail.setOnClickListener(v -> {
            addFragment(UserInfoFragment.newInstance(),true);
            binding.dlLayout.closeDrawer(GravityCompat.START);
        });
        binding.btnDrawer.setOnClickListener(v -> {
            if (binding.dlLayout.isDrawerOpen(GravityCompat.START)) {
                binding.dlLayout.closeDrawer(GravityCompat.START);
            } else {
                binding.dlLayout.openDrawer(GravityCompat.START);
                binding.navHeaderMain.navHeaderMain.bringToFront();
                binding.navHeaderMain.navHeaderMain.requestFocus();
            }
        });

        binding.navHeaderMain.btnHome.setOnClickListener(v -> {
            binding.dlLayout.closeDrawer(GravityCompat.START);
        });

        binding.btnAdd.setOnClickListener(v -> {
            openActivity(AddDeviceActivity.class);
            viewModel.queryDeviceType();

        });
    }

    private void onClickNavigationDrawer() {
        Window window = getWindow();
        binding.dlLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                float angle = slideOffset * 180;
                binding.btnDrawer.setRotation(angle);
                binding.navHeaderMain.navHeaderMain.bringToFront();
                binding.navHeaderMain.navHeaderMain.requestFocus();
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                binding.btnDrawer.setRotation(90f);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    window.setStatusBarColor(getColor(R.color.transparent));
                };

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                binding.btnDrawer.setRotation(0f);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    window.setStatusBarColor(getColor(R.color.blue_gradient_end));
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }
}