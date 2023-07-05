package bk.ltuddd.iotapp.utils.view;

import android.app.Activity;
import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.util.function.Consumer;

import bk.ltuddd.iotapp.R;
import bk.ltuddd.iotapp.databinding.DialogDevicePasswordBinding;

public class DialogView {

    public static void showDialogSerial(
            Activity activity,
            final Consumer<String> listenerPositive
    ) {
        try {
            DialogDevicePasswordBinding binding = DialogDevicePasswordBinding.inflate(LayoutInflater.from(activity));

            final Dialog dialog = new Dialog(activity, R.style.AppThemeNew_DialogTheme);
            dialog.setCancelable(false);
            dialog.setContentView(binding.getRoot());

            binding.btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listenerPositive.accept(binding.edtSerial.getText().toString().trim());
                }
            });

            binding.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        } catch (Exception e) {
            // Handle exception
        }
    }

}
