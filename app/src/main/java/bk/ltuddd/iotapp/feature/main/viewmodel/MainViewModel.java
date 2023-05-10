package bk.ltuddd.iotapp.feature.main.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import bk.ltuddd.iotapp.R;
import bk.ltuddd.iotapp.core.base.BaseViewModel;
import bk.ltuddd.iotapp.data.model.User;
import bk.ltuddd.iotapp.feature.main.repository.MainRepository;
import bk.ltuddd.iotapp.feature.main.repository.MainRepositoryImpl;
import bk.ltuddd.iotapp.utils.livedata.SingleLiveEvent;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends BaseViewModel {

    private final MainRepository mainRepository = new MainRepositoryImpl();

    public SingleLiveEvent<Boolean> updateUserSuccess = new SingleLiveEvent<>();

    private MutableLiveData<User> _userInfo = new MutableLiveData<>();

    public LiveData<User> userInfo() {
        return _userInfo;
    }

    public void updateUserInfo(User user, String userUid) {
        compositeDisposable.add(
        mainRepository.updateUserInfo(user, userUid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> updateUserSuccess.setValue(true), throwable -> updateUserSuccess.setValue(false)
                ));
    }

    public void queryUserInfo(String userUid) {
        compositeDisposable.add(
                mainRepository.queryUser(userUid)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userInfo -> {
                                    _userInfo.setValue(userInfo);
                                }, throwable -> {
                                    setErrorStringId(R.string.error_send_otp_from_firebase);
                                }
                        )
        );
    }



}
