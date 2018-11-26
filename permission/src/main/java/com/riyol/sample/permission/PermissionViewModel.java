package com.riyol.sample.permission;

import android.app.Application;

import com.riyol.permission.PermissionHelper;
import com.riyol.viewmodel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission;

public class PermissionViewModel extends BaseViewModel {
    // TODO: Implement the ViewModel


    public PermissionViewModel(Application application) {
        super(application);
    }

    public List<PermissionModel> dataList() {
        List<PermissionModel> list = new ArrayList<>();

        list.add(new PermissionModel("Storage", permission.WRITE_EXTERNAL_STORAGE));
        list.add(new PermissionModel("Camera", permission.CAMERA, permission.WRITE_EXTERNAL_STORAGE));
        list.add(new PermissionModel("Location", permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION));
        list.add(new PermissionModel("Calendar", permission.WRITE_CALENDAR, permission.READ_CALENDAR));

        for (PermissionModel model : list) {
            model.setState(PermissionHelper.check(getApplication(), model.getPermission()));
        }
        return list;
    }
}
