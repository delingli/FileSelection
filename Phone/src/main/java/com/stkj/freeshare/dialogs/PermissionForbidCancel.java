package com.stkj.freeshare.dialogs;

import android.content.Context;
import android.support.annotation.NonNull;

import com.stkj.freeshare.R;
import com.stkj.freeshare.models.dialogs.DialogNormalModel;

public abstract class PermissionForbidCancel extends NormalDialog {
    protected PermissionForbidCancel(@NonNull Context context, String permissions) {
        super(context, new DialogNormalModel(
                context.getResources().getString(R.string.permission_forbid_title),
                context.getResources().getString(R.string.permission_forbid_desc, permissions),
                context.getResources().getString(R.string.permission_forbid_goto),
                context.getResources().getString(R.string.permission_forbid_not_yet)
        ));
    }
}