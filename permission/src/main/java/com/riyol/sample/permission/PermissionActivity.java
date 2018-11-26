package com.riyol.sample.permission;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.riyol.base.activity.VMBindingMaterialActivity;
import com.riyol.base.dialog.MaterialAlertDialog;
import com.riyol.function.Optional;
import com.riyol.permission.PermissionHelper;
import com.riyol.viewmodel.BaseViewModel;

import java.util.List;

public abstract class PermissionActivity<VM extends BaseViewModel, VB extends ViewDataBinding>
        extends VMBindingMaterialActivity<VM, VB> {
    private PermissionHelper permissionHelper;

    /**
     * Runtime permiss
     **/
    protected PermissionHelper permissionHelper() {
        if (permissionHelper == null) {
            permissionHelper = new PermissionHelper();
        }
        permissionHelper
                .onRationale(rationaleCallback)
                .onDenied(deniedCallback)
                .onProhibit(prohibitCallback);
        return permissionHelper;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {

        if (permissionHelper != null) {
            if (permissionHelper.onRequestPermissionsResult(this, requestCode, permissions, grantResults)) {
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private PermissionHelper.RationaleCallback rationaleCallback = (permissions) -> {
        List<String> message = PermissionUtil.transformText(getApplicationContext(), permissions);
        MaterialAlertDialog dialog = MaterialAlertDialog.newBuilder(getApplicationContext())
                .setTitle(R.string.permission_rationale_title)
                .setMessage(String.format(getString(R.string.permission_rationale_message),
                        TextUtils.join("\n", message)))
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", (tag, which) ->
                        Optional.ofNullable(permissionHelper).ifPresent(h -> h.requestDisallowIntercept(this))
                )
                .build();
        dialog.show(this);

    };
    private PermissionHelper.DeniedCallback deniedCallback = permissions -> {
        List<String> message = PermissionUtil.transformText(getApplicationContext(), permissions);
        MaterialAlertDialog dialog = MaterialAlertDialog.newBuilder(getApplicationContext())
                .setTitle(R.string.permission_denied_title)
                .setMessage(String.format(getString(R.string.permission_rationale_message),
                        TextUtils.join("\n", message)))
                .setNegativeButton("取消", null)
                .setPositiveButton("设置", (tag, which) -> {
                    startActivity(PermissionUtil.permissionSetting(getApplicationContext()));
                })
                .build();
        dialog.show(this);
    };

    private PermissionHelper.ProhibitCallback prohibitCallback = permissions -> {
        deniedCallback.onPermissionDenied(permissions);
    };
}
