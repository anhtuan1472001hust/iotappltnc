package bk.ltuddd.iotapp.feature.auth.repository;

import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;

import bk.ltuddd.iotapp.data.model.User;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by @Author: TuanNNA
 * Create Time : 11:00 - 28/04/2023
 * Repository chứa những phương thức để lấy dữ liệu từ remote hoặc local
 */

public interface AuthRepository {
    Completable sendOtp(PhoneAuthOptions phoneAuthOptions);
    Completable createUser(User user);
    Single<Boolean> verifyOtp(PhoneAuthCredential phoneAuthCredential,String otpCode);
    Single<User> login(String phoneNumber, String password);
    Single<Boolean> checkExistAccount(String phoneNumber);
}
