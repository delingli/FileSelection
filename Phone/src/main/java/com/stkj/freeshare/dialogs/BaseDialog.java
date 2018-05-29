package com.stkj.freeshare.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.stkj.freeshare.R;

abstract class BaseDialog extends Dialog {
    BaseDialog(@NonNull Context context) {
        super(context, R.style.FSDialog);
    }
}