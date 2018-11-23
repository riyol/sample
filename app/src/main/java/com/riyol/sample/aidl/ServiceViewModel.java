package com.riyol.sample.aidl;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;

import com.riyol.aidl.IUserService;
import com.riyol.aidl.entity.User;
import com.riyol.rx.RxBindService;
import com.riyol.sample.aidl.service.remote.RemoteUserService;
import com.riyol.viewmodel.BaseViewModel;

import java.util.Collections;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;

public class ServiceViewModel extends BaseViewModel implements IUserListDataModel {
    private Disposable disposable;

    private final RxBindService bindServiceSingle;

    private MutableLiveData<List<User>> userList1 = new MutableLiveData<>();

    public ServiceViewModel(@NonNull Application application) {
        super(application);

        Intent remoteServiceIntent = new Intent(application, RemoteUserService.class);
        bindServiceSingle = RxBindService.create(application.getBaseContext(), remoteServiceIntent);
    }

    public void getRemote1UserList() {
        bindServiceSingle.map(serviceMapper).map(service -> {
            List<User> list = service.getAllUsers();
            if (list == null) {
                list = Collections.emptyList();
            }
            return list;
//            userList1.setValue(list);
        }).subscribe(new SingleObserver<List<User>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(List<User> users) {
                userList1.setValue(users);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        });
    }

    public void setUserList(List<User> list) {
        userList1.setValue(list);
    }

    @Override
    public MutableLiveData<List<User>> getDataList() {
        return userList1;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    private Function<? super IBinder, IUserService> serviceMapper = (service) -> {
        ObjectHelper.requireNonNull(service, "iBinder is null");
        return IUserService.Stub.asInterface(service);
    };


}
