package com.stkj.freeshare.dialogs;

import android.content.Context;
import android.support.annotation.NonNull;

import com.stkj.freeshare.R;
import com.stkj.freeshare.models.dialogs.DialogNormalModel;

public abstract class PermissionGrantDialog extends NormalDialog {
    protected PermissionGrantDialog(@NonNull Context context, String permissions) {
        super(context, new DialogNormalModel(
                context.getResources().getString(R.string.permission_grant_title),
                context.getResources().getString(R.string.permission_grant_desc, permissions),
                context.getResources().getString(R.string.permission_grant_known),
                null
        ));
    }

    @Override
    protected final void onClickEnsure() {
        onKnownClicked();
    }

    protected abstract void onKnownClicked();

    @Override
    protected final void onClickCancel() {

    }
}