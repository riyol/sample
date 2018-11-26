package com.riyol.sample.permission;

import android.graphics.Color;
import android.os.Bundle;

import com.riyol.databinding.BindingUtil;
import com.riyol.databinding.recyclerview.adatper.ListBindingAdapter;
import com.riyol.function.Supplier;
import com.riyol.sample.permission.databinding.PermissionItemBinding;
import com.riyol.sample.permission.databinding.PermissionListBinding;
import com.riyol.utils.ToastUtil;
import com.riyol.view.divider.Divider;

public class MainActivity extends PermissionActivity<PermissionViewModel, PermissionListBinding>
        implements PermissionPresenter {

    @Override
    protected void setUpView(Bundle savedState) {
        super.setUpView(savedState);
        BindingUtil.setGridLayoutManager(viewBinding.list, 2);
        viewBinding.list.addItemDecoration(Divider.getDivider(Color.TRANSPARENT));
        viewBinding.list.setAdapter(new PermissionAdapter(this));
    }

    @Override
    protected int bindingLayoutRes() {
        return R.layout.permission_list;
    }

    @Override
    public Class<PermissionViewModel> provideViewModelClass() {
        return PermissionViewModel.class;
    }

    @Override
    public Supplier<PermissionViewModel> provideViewModelSupplier() {
        return () -> new PermissionViewModel(getApplication());
    }

    @Override
    public void onPermissionItemClick(PermissionModel model) {
        permissionHelper()
                .onGranted(() -> {
                    model.setState(true);
                    ToastUtil.showToast(getApplicationContext(), "permission grand");
                })
                .needPermission(model.getPermission())
                .request(this);
    }

    public static class PermissionAdapter extends ListBindingAdapter<PermissionItemBinding> {
        private final PermissionPresenter presenter;

        public PermissionAdapter(PermissionPresenter presenter) {
            this.presenter = presenter;
        }

        @Override
        protected int layoutRes() {
            return R.layout.permission_item;
        }

        @Override
        protected void onBindingView(PermissionItemBinding binding, int position) {
            super.onBindingView(binding, position);
            binding.setPresenter(presenter);
        }
    }
}
