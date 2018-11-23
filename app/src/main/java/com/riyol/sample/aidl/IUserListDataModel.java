package com.riyol.sample.aidl;

import android.arch.lifecycle.MutableLiveData;

import com.riyol.aidl.entity.User;

import java.util.List;

public interface IUserListDataModel {
    MutableLiveData<List<User>> getDataList();
}
