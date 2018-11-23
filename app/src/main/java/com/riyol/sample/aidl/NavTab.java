package com.riyol.sample.aidl;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import com.riyol.EnumMap.EnumCode;
import com.riyol.EnumMap.EnumCodeMap;
import com.riyol.sample.R;

public enum NavTab implements EnumCode {
    REMOTE_SERVICE1(R.string.remote_service_1) {
        @NonNull
        @Override
        public Fragment newInstance() {
            return ListFragment.newInstance();
        }
    },
//    REMOTE_SERVICE2(R.string.remote_service_2) {
//        @NonNull
//        @Override
//        public Fragment newInstance() {
//            return null;
//        }
//    },
//    LOCAL_SERVICE(R.string.local_service_1) {
//        @NonNull
//        @Override
//        public Fragment newInstance() {
//            return null;
//        }
//    }
    ;

    NavTab(int strRes) {
        this.strRes = strRes;
    }

    @StringRes
    private final int strRes;

    @NonNull
    public abstract Fragment newInstance();

    @Override
    public int code() {
        return ordinal();
    }

    public int getStrRes() {
        return strRes;
    }

    private static final EnumCodeMap<NavTab> MAP = new EnumCodeMap<>(NavTab.class);
    @NonNull
    public static NavTab of(int code) {
        return MAP.get(code);
    }

    public static int size() {
        return MAP.size();
    }
}
