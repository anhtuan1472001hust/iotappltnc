package bk.ltuddd.iotapp.feature.main.repository;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bk.ltuddd.iotapp.data.model.DeviceModel;
import bk.ltuddd.iotapp.data.model.DeviceType;
import bk.ltuddd.iotapp.data.model.Dht11;
import bk.ltuddd.iotapp.data.model.Lamp;
import bk.ltuddd.iotapp.data.model.User;
import io.reactivex.Completable;
import io.reactivex.Single;

public class MainRepositoryImpl implements MainRepository {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    @Override
    public Completable updateUserInfo(User user, String userUid) {
        Map<String, Object> updateValues = new HashMap<>();
        updateValues.put("name", user.getName());
        updateValues.put("email", user.getEmail());
        updateValues.put("address", user.getAddress());
        updateValues.put("birthday", user.getBirthday());
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
        return Single.create(emitter -> {
            firebaseDatabase.getReference("users")
                    .child(userUid)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                User user = snapshot.getValue(User.class);
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

    @Override
    public Single<List<String>> queryDeviceType() {
        return Single.create(emitter -> {
            firebaseDatabase.getReference("DeviceType")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<String> deviceTypeList = new ArrayList<>();
                            if (snapshot.exists()) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    String deviceType = dataSnapshot.getKey();
                                    deviceTypeList.add(deviceType);
                                    }

                            }
                            emitter.onSuccess(deviceTypeList);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            emitter.onError(error.toException());
                        }
                    });
        });
    }

    @Override
    public Single<List<DeviceModel>> getListDevice() {
        return Single.create(emitter -> {
            firebaseDatabase.getReference("Device")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<DeviceModel> listDeviceModel = new ArrayList<>();
                            if (snapshot.exists()) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    DeviceModel deviceModel = dataSnapshot.getValue(DeviceModel.class);
                                    listDeviceModel.add(deviceModel);
                                }
                                emitter.onSuccess(listDeviceModel);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            emitter.onError(error.toException());
                        }
                    });
        });
    }

    @Override
    public Single<Boolean> updateStateLamp(long serial, int state) {
        DatabaseReference deviceRef = FirebaseDatabase.getInstance().getReference("Device");
        return Single.create(emitter -> {
            deviceRef.orderByChild("serial")
                    .equalTo(serial)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String deviceKey = dataSnapshot.getKey();
                                DatabaseReference databaseReference = deviceRef.child(deviceKey).child("state");
                                databaseReference.setValue(state)
                                        .addOnCompleteListener(task -> {
                                            if (task.isSuccessful()) {
                                                emitter.onSuccess(true);
                                            } else {
                                                emitter.onError(task.getException());
                                            }
                                        });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            emitter.onError(error.toException());
                        }
                    });
        });
    }

//    @Override
//    public Single<Integer> getLampState() {
//        return Single.create(emitter -> {
//            firebaseDatabase.getReference("Device")
//        });
//    }

//    @Override
//    public Single<List<Dht11>> getListDHT11() {
//        return Single.create(emitter -> {
//            firebaseDatabase.getReference("Device")
//                    .child("DHT11")
//                    .addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            List<Dht11> listDht11 = new ArrayList<>();
//                            if (snapshot.exists()) {
//                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                                    Dht11 dht11 = dataSnapshot.getValue(Dht11.class);
//                                    listDht11.add(dht11);
//                                }
//                                emitter.onSuccess(listDht11);
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//                            emitter.onError(error.toException());
//                        }
//                    });
//
//        });
//    }
//
//    @Override
//    public Single<List<Lamp>> getListLamp() {
//        return Single.create(emitter -> {
//            firebaseDatabase.getReference("Device")
//                    .child("LIGHT")
//                    .addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            List<Lamp> listLamp = new ArrayList<>();
//                            if (snapshot.exists()) {
//                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                                    Lamp lamp = dataSnapshot.getValue(Lamp.class);
//                                    listLamp.add(lamp);
//                                }
//                                emitter.onSuccess(listLamp);
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//                            emitter.onError(error.toException());
//                        }
//                    });
//
//        });
//    }

//    @Override
//    public Single<String> queryDeviceBySerial(String serial) {
//        return Single.create(emitter -> {
//            DatabaseReference databaseReference = firebaseDatabase.getReference("Device").child(deviceType);
//            Query query = databaseReference.orderByChild("serial");
//            firebaseDatabase.getReference("Device")
//                    .child(deviceType)
//                    .addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            if (snapshot.exists()) {
//                                String serial = snapshot.getValue(String.class);
//                                emitter.onSuccess(serial);
//                            }
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//                            emitter.onError(error.toException());
//                        }
//                    });
//        });
//    }


}
