package bk.ltuddd.iotapp.feature.main.ui.adapter;

import static bk.ltuddd.iotapp.utils.Constant.VIEW_TYPE_DHT11;
import static bk.ltuddd.iotapp.utils.Constant.VIEW_TYPE_LAMP;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import bk.ltuddd.iotapp.R;
import bk.ltuddd.iotapp.core.recycleview.BaseRecycleAdapter;
import bk.ltuddd.iotapp.core.recycleview.BaseViewHolder;
import bk.ltuddd.iotapp.data.model.DeviceModel;

import bk.ltuddd.iotapp.databinding.ItemDht11Binding;
import bk.ltuddd.iotapp.databinding.ItemLampBinding;
import bk.ltuddd.iotapp.utils.Constant;

public class MainAdapter extends BaseRecycleAdapter<DeviceModel> {

    private boolean state = false;
    private boolean isLightOn = false;
    private OnBtnStateLampClick onBtnStateLampClick;

    @Override
    public BaseViewHolder<?> createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
         if (viewType == VIEW_TYPE_DHT11) {
            ItemDht11Binding dht11Binding = ItemDht11Binding.inflate(inflater, parent, false);
            return new DHT11ViewHolder(dht11Binding);
        } else if (viewType == VIEW_TYPE_LAMP) {
            ItemLampBinding lampBinding = ItemLampBinding.inflate(inflater, parent, false);
            return new LampViewHolder(lampBinding);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        DeviceModel deviceModel = mData.get(position);
        if (deviceModel != null) {
            if (deviceModel.getType().equals(Constant.DHT11)) {
                return VIEW_TYPE_DHT11;
            } else if (deviceModel.getType().equals(Constant.SMART_LIGHT)) {
                return VIEW_TYPE_LAMP;
            }
        }
        return super.getItemViewType(position);
    }

    public class DHT11ViewHolder extends BaseViewHolder<ItemDht11Binding> {

        public DHT11ViewHolder(@NonNull ItemDht11Binding viewBinding) {
            super(viewBinding);
        }

        @Override
        public void bindData(int position) {
            DeviceModel deviceModel = mData.get(position);
            getViewBinding().tvNameDevice.setText(deviceModel.getName());
            getViewBinding().tvHumidity.setText(formatHumid(deviceModel.getHumid()));
            getViewBinding().tvTemperature.setText(formatTemp(deviceModel.getTemp()));
            getViewBinding().imageDevice.setImageResource(R.drawable.sensor);

        }
    }

    public class LampViewHolder extends BaseViewHolder<ItemLampBinding> {

        public LampViewHolder(@NonNull ItemLampBinding viewBinding) {
            super(viewBinding);
        }

        @Override
        public void bindData(int position) {
            DeviceModel deviceModel = mData.get(position);
            getViewBinding().tvNameDevice.setText(deviceModel.getName());
            getViewBinding().imageDevice.setImageResource(R.drawable.smartlight);
            if (deviceModel.getState() == 1) {
                getViewBinding().btnOnOff.setSelected(true);
                getViewBinding().tvState.setText(itemView.getContext().getString(R.string.state_on));
                getViewBinding().tvState.setTextColor(itemView.getContext().getColor(R.color.green_light));
                isLightOn = true;
            } else {
                getViewBinding().btnOnOff.setSelected(false);
                getViewBinding().tvState.setText(itemView.getContext().getString(R.string.state_off));
                getViewBinding().tvState.setTextColor(itemView.getContext().getColor(R.color.grey_light));
                isLightOn = false;

            }
            getViewBinding().btnOnOff.setOnClickListener(v -> {
                if (isLightOn) {
                    onBtnStateLampClick.setOnClickBtnState(deviceModel.getSerial(),0);
                    isLightOn = false;
                } else {
                    onBtnStateLampClick.setOnClickBtnState(deviceModel.getSerial(),1);
                    isLightOn = true;
                }
                state = !state;
                getViewBinding().btnOnOff.setSelected(state);
                if (state) {
                    getViewBinding().tvState.setText(itemView.getContext().getString(R.string.state_on));
                    getViewBinding().tvState.setTextColor(itemView.getContext().getColor(R.color.green_light));
                } else {
                    getViewBinding().tvState.setText(itemView.getContext().getString(R.string.state_off));
                    getViewBinding().tvState.setTextColor(itemView.getContext().getColor(R.color.grey_light));

                }
            });
        }
    }

    private String formatHumid(double humid) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(humid).append(" ").append("%");
        return stringBuilder.toString();
    }

    private String formatTemp(double temp) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(temp).append(" ").append("°C");
        return stringBuilder.toString();
    }

    public interface OnBtnStateLampClick {
        void setOnClickBtnState(long serial, int state);
    }

    public void setOnBtnStateLampClick(OnBtnStateLampClick onBtnStateLampClick) {
        this.onBtnStateLampClick = onBtnStateLampClick;
    }
}
