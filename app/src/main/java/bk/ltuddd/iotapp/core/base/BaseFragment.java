package bk.ltuddd.iotapp.core.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

/**
 * Created by @Author: TuanNNA
 * Create Time : 11:00 - 28/04/2023
 * Lớp BaseFragment để mọi class Fragment khác kế thừa
 * Các hàm trừu tượng (abstract function) là các hàm bắt buộc phải override
 */

public abstract class BaseFragment<VB extends ViewBinding,VM extends BaseViewModel> extends Fragment implements BaseBehavior {

    protected VB viewBinding;

    protected VM viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewBinding = getBinding(inflater);
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(getViewModelClass());
        onCommonViewLoaded();
        addViewListener();
    }

    protected abstract VB getBinding(LayoutInflater inflater);

    protected abstract Class<VM> getViewModelClass();

    /**
     *  Hàm này để mở 1 activity khác
     */
    protected void openActivity(Class<?> cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }

    /**
     *  Hàm này để mở 1 activity khác và gửi dữ liệu kèm theo
     */
    protected void openActivity(Class<?> cls, Bundle extras) {
        Intent intent = new Intent(getActivity(),cls);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivity(intent);
    }

    protected void openActivity(Class<?> cls,int... flags) {
        Intent intent = new Intent(getActivity(),cls);
        for (int flag : flags) {
            intent.addFlags(flag);
        }
        startActivity(intent);
    }


}
