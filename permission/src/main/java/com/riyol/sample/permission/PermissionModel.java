package com.riyol.sample.permission;

import android.databinding.ObservableBoolean;

public class PermissionModel {
    private String name;
    private String[] permission;

    private ObservableBoolean state = new ObservableBoolean(false);

    public PermissionModel(String name, String... permission) {
        this.name = name;
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getPermission() {
        return permission;
    }

    public void setPermission(String[] permission) {
        this.permission = permission;
    }

    public ObservableBoolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state.set(state);
    }
}
