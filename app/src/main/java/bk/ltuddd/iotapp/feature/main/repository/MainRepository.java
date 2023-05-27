package bk.ltuddd.iotapp.feature.main.repository;

import java.util.List;

import bk.ltuddd.iotapp.data.model.DeviceModel;
import bk.ltuddd.iotapp.data.model.DeviceType;
import bk.ltuddd.iotapp.data.model.Dht11;
import bk.ltuddd.iotapp.data.model.Lamp;
import bk.ltuddd.iotapp.data.model.User;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface MainRepository {

    Completable updateUserInfo(User user, String userUid);
    Single<User> queryUser(String userUid);
    Single<List<String>> queryDeviceType();
//    Single<Device> queryDeviceBySerial(String serial);
//    Single<List<Dht11>> getListDHT11();
//    Single<List<Lamp>> getListLamp();
    Single<List<DeviceModel>> getListDevice();
//    Single<Integer> getLampState();
    Single<Boolean> updateStateLamp(long serial,int state);

}
