package bk.ltuddd.iotapp.feature.main.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import bk.ltuddd.iotapp.R;
import bk.ltuddd.iotapp.core.base.BaseViewModel;
import bk.ltuddd.iotapp.data.model.DeviceModel;
import bk.ltuddd.iotapp.data.model.Dht11;
import bk.ltuddd.iotapp.data.model.Lamp;
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

    private MutableLiveData<List<String>> _deviceType = new MutableLiveData<>();

    public LiveData<List<String>> deviceType() {
        return _deviceType;
    }

    private MutableLiveData<String> _numberSerial = new MutableLiveData<>();

    public LiveData<String> numberSerial() {
        return _numberSerial;
    }

    private MutableLiveData<List<Dht11>> _listDht11 = new MutableLiveData<>();
    public LiveData<List<Dht11>> listDht11() {
        return _listDht11;
    }

    private MutableLiveData<List<Lamp>> _listLamp = new MutableLiveData<>();
    public LiveData<List<Lamp>> listLamp() {
        return _listLamp;
    }

    private MutableLiveData<List<DeviceModel>> _listDeviceModel = new MutableLiveData<>();
    public LiveData<List<DeviceModel>> listDeviceModel() {
        return _listDeviceModel;
    }

    private SingleLiveEvent<Boolean> isUpdateSuccess = new SingleLiveEvent<>();


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

                                }
                        )
        );
    }

    public void queryDeviceType() {
        compositeDisposable.add(
                mainRepository.queryDeviceType()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                deviceType -> _deviceType.setValue(deviceType),
                                throwable -> setErrorStringId(R.string.error_send_otp_from_firebase)
                        )
        );
    }

    public void getListDevice() {
        compositeDisposable.add(
                mainRepository.getListDevice()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                listDeviceModel -> _listDeviceModel.setValue(listDeviceModel),
                                throwable -> setErrorStringId(R.string.error_send_otp_from_firebase)
                        )
        );
    }

    public void updateLampState(long serial, int state) {
        compositeDisposable.add(
                mainRepository.updateStateLamp(serial,state)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                isSuccess -> isUpdateSuccess.setValue(isSuccess),
                                throwable -> setErrorStringId(R.string.error_send_otp_from_firebase)
                        )
        );
    }

//    public void getListDHT11() {
//        compositeDisposable.add(
//                mainRepository.getListDHT11()
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                listDht11 -> _listDht11.setValue(listDht11),
//                                throwable ->  setErrorStringId(R.string.error_send_otp_from_firebase)
//                        )
//        );
//    }
//
//    public void getListLamp() {
//        compositeDisposable.add(
//                mainRepository.getListLamp()
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                listLamp -> _listLamp.setValue(listLamp),
//                                throwable ->  setErrorStringId(R.string.error_send_otp_from_firebase)
//                        )
//        );
//    }
//
//    public void queryDeviceBySerial(String serial) {
//        compositeDisposable.add(
//                mainRepository.queryDeviceBySerial(serial)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                serial -> {
//                                    _numberSerial.setValue(serial);
//                                }, throwable ->{
//                                    setErrorStringId(R.string.error_send_otp_from_firebase);
//                                }
//                        )
//        );
//
//    }



}
