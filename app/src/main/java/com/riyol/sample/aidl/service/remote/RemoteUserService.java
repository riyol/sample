package com.riyol.sample.aidl.service.remote;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.riyol.aidl.impl.UserServiceImpl;

public class RemoteUserService extends Service {
    private UserServiceImpl serviceImpl;

    @Override
    public void onCreate() {
        super.onCreate();
        serviceImpl = new UserServiceImpl(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return serviceImpl;
    }
}
