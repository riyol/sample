package com.riyol.sample.aidl.service.local;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.riyol.aidl.IUserService;
import com.riyol.aidl.entity.User;
import com.riyol.aidl.impl.UserServiceImpl;

import java.util.List;

public class LocalUserService extends Service implements IUserService {
    private final IBinder binder = new LocalBinder();
    private UserServiceImpl serviceImpl;

    @Override
    public void onCreate() {
        super.onCreate();
        serviceImpl = new UserServiceImpl(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class LocalBinder extends Binder {
        LocalUserService getService() {
            return LocalUserService.this;
        }
    }

    @Override
    public void initUsers() throws RemoteException {
        serviceImpl.initUsers();
    }

    @Override
    public List<User> getAllUsers() throws RemoteException {
        return serviceImpl.getAllUsers();
    }

    @Override
    public List<User> filter(int sex) throws RemoteException {
        return serviceImpl.filter(sex);
    }

    @Override
    public boolean deleteUser(int id) throws RemoteException {
        return serviceImpl.deleteUser(id);
    }

    @Override
    public boolean addUser(User user) throws RemoteException {
        return serviceImpl.addUser(user);
    }

    @Override
    public boolean getUserInfo(int id, User user) throws RemoteException {
        return serviceImpl.getUserInfo(id, user);
    }

    @Override
    public boolean updateUser(int id, User user) throws RemoteException {
        return serviceImpl.updateUser(id, user);
    }

    @Override
    public IBinder asBinder() {
        return null;
    }
}
