package bk.ltuddd.iotapp.feature.main.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import bk.ltuddd.iotapp.data.model.User;
import io.reactivex.Completable;
import io.reactivex.Single;

public class MainRepositoryImpl implements MainRepository {
    @Override
    public Completable updateUserInfo(User user, String userUid) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        Map<String, Object> updateValues = new HashMap<>();
        updateValues.put("name", user.getName());
        updateValues.put("email", user.getEmail());
        updateValues.put("address", user.getAddress());
        updateValues.put("birthday",user.getBirthday());
        return Completable.create(emitter -> {
            firebaseDatabase.getReference("users")
                    .child(userUid)
                    .updateChildren(updateValues)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onComplete();
                        } else {
                            emitter.onError(new Throwable(task.getException()));
                        }
                    });
        });
    }

    @Override
    public Single<User> queryUser(String userUid) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        return Single.create(emitter -> {
            firebaseDatabase.getReference("users")
                    .child(userUid)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                    User user = snapshot.getValue(User.class);
                                    Log.e("email",user.getEmail());
                                    if (user != null) {
                                        emitter.onSuccess(user);
                                        return;
                                    }
                            }
                            emitter.onError(new Exception("Query failed"));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            emitter.onError(error.toException());
                        }
                    });
        });
    }
}
