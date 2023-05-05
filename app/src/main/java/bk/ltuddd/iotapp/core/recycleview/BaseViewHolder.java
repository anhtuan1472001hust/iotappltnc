package bk.ltuddd.iotapp.core.recycleview;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

abstract class BaseViewHolder<T, VB extends ViewBinding> extends RecyclerView.ViewHolder {

    private final VB viewBinding;

    public BaseViewHolder(@NonNull View itemView, @NonNull VB viewBinding) {
        super(viewBinding.getRoot());
        this.viewBinding = viewBinding;
    }

    public VB getViewBinding() {
        return viewBinding;
    }

    public abstract void bindData(T data);
}
