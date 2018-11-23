package com.riyol.sample.aidl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.riyol.base.fragment.VMBindingFragment;
import com.riyol.databinding.BindingUtil;
import com.riyol.databinding.recyclerview.adatper.ListBindingAdapter;
import com.riyol.sample.R;
import com.riyol.sample.databinding.FragmentListBinding;
import com.riyol.sample.databinding.UserItemBinding;

public class ListFragment extends VMBindingFragment<ServiceViewModel, FragmentListBinding> {


    public static Fragment newInstance() {
        return new ListFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BindingUtil.setVerticalLayoutManager(viewBinding.recycleView);
        viewBinding.recycleView.setAdapter(new UserListAdapter());
        viewModel.getRemote1UserList();
    }

    @Override
    protected boolean shouldShareViewModel() {
        return true;
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_list;
    }

    @Override
    public Class<ServiceViewModel> provideViewModelClass() {
        return ServiceViewModel.class;
    }


    private class UserListAdapter extends ListBindingAdapter<UserItemBinding> {

        @Override
        protected int layoutRes() {
            return R.layout.user_item;
        }
    }
}
