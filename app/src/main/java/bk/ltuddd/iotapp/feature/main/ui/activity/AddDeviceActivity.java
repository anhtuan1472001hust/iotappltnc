package bk.ltuddd.iotapp.feature.main.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import bk.ltuddd.iotapp.R;
import bk.ltuddd.iotapp.core.base.BaseActivity;
import bk.ltuddd.iotapp.data.model.DeviceType;
import bk.ltuddd.iotapp.databinding.ActivityAddDeviceBinding;
import bk.ltuddd.iotapp.feature.main.ui.adapter.AddDeviceAdapter;
import bk.ltuddd.iotapp.feature.main.viewmodel.MainViewModel;
import bk.ltuddd.iotapp.utils.Constant;
import bk.ltuddd.iotapp.utils.view.DialogConfirmFragment;

public class AddDeviceActivity extends BaseActivity<ActivityAddDeviceBinding, MainViewModel> {

    private AddDeviceAdapter addDeviceAdapter = new AddDeviceAdapter();
    private GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
//    private DialogConfirmFragment dialogConfirmFragment = new DialogConfirmFragment();
    private String serialNumber;

    @Override
    protected ActivityAddDeviceBinding getViewBinding() {
        return ActivityAddDeviceBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<MainViewModel> getViewModelClass() {
        return MainViewModel.class;
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.deviceType().observe(this, listDeviceType -> {
            addDeviceAdapter.submitList(listDeviceType);
        });
        viewModel.numberSerial().observe(this, serial -> {
            serialNumber = serial;
        });

    }

    @Override
    public void onCommonViewLoaded() {
        viewModel.queryDeviceType();
        binding.rcvDevice.setAdapter(addDeviceAdapter);
        binding.rcvDevice.setLayoutManager(gridLayoutManager);
//        addDeviceAdapter.setOnItemClickListener(new AddDeviceAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick() {
//                dialogConfirmFragment.show(getSupportFragmentManager(),DialogConfirmFragment.class.getSimpleName());
//            }
//        });
//        dialogConfirmFragment.setOnPasswordEnteredListener(password -> {
//            if (serialNumber.equals(password)) {
//                openActivity(MainActivity.class,Intent.FLAG_ACTIVITY_CLEAR_TOP, Intent.FLAG_ACTIVITY_CLEAR_TASK, Intent.FLAG_ACTIVITY_NEW_TASK);
//            }
//        });
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.light_background));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

    }

    @Override
    public void addViewListener() {
        binding.toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });

    }
}
