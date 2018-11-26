package com.riyol.sample.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.riyol.aidl.IUserService;
import com.riyol.aidl.entity.User;
import com.riyol.base.activity.VMBindingMaterialActivity;
import com.riyol.function.Optional;
import com.riyol.function.Supplier;
import com.riyol.sample.R;
import com.riyol.sample.aidl.service.remote.RemoteUserService;
import com.riyol.sample.databinding.MainContentLayoutBinding;

import java.util.List;

public class MainActivity extends VMBindingMaterialActivity<ServiceViewModel, MainContentLayoutBinding> {
    private static final String TAG = MainActivity.class.getSimpleName();

    private boolean canBind = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        canBind = bindService(new Intent(this, RemoteUserService.class), connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        if (canBind) {
            unbindService(connection);
            canBind = false;
        }
        super.onStop();
    }

    @Override
    protected boolean displayHomeAsUpEnable() {
        return false;
    }

    @Override
    protected void setUpView(Bundle savedState) {
        super.setUpView(savedState);
        viewBinding.viewPager.setAdapter(new TabFragmentPagerAdapter(getSupportFragmentManager()));
        viewBinding.viewPager.setOffscreenPageLimit(3);
        Optional.ofNullable(getTabLayout()).ifPresent(tab -> tab.setupWithViewPager(viewBinding.viewPager));
    }

    @Override
    protected int bindingLayoutRes() {
        return R.layout.main_content_layout;
    }

    @Override
    public Class<ServiceViewModel> provideViewModelClass() {
        return ServiceViewModel.class;
    }

    @Override
    public Supplier<ServiceViewModel> provideViewModelSupplier() {
        return () -> new ServiceViewModel(getApplication());
    }


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.w(TAG, "onServiceConnected:" + name);
            IUserService userService = IUserService.Stub.asInterface(service);
            try {
                List<User> list = userService.getAllUsers();
                viewModel.setUserList(list);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected:" + name);
        }

        @Override
        public void onBindingDied(ComponentName name) {

        }
    };

    private class TabFragmentPagerAdapter extends FragmentPagerAdapter {
        public TabFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return NavTab.of(position).newInstance();
        }

        @Override
        public int getCount() {
            return NavTab.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return getString(NavTab.of(position).getStrRes());
        }
    }
}
