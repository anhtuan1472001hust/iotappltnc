package bk.ltuddd.iotapp.feature.main.repository;

import bk.ltuddd.iotapp.data.model.User;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface MainRepository {

    Completable updateUserInfo(User user, String userUid);
    Single<User> queryUser(String userUid);

}
