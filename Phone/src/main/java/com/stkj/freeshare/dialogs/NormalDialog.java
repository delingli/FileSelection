package com.stkj.freeshare.dialogs;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import com.stkj.freeshare.R;
import com.stkj.freeshare.databinding.DialogNormalBinding;
import com.stkj.freeshare.models.dialogs.DialogNormalModel;

abstract class NormalDialog extends BaseDialog {
    NormalDialog(@NonNull Context context, DialogNormalModel model) {
        super(context);
        setCanceledOnTouchOutside(false);
        final DialogNormalBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.dialog_normal, null, false);
        setContentView(binding.getRoot());
        binding.setModel(model);
        binding.dialogNormalLeftTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(final View view) {
                dismiss();
                onClickEnsure();
            }
        });
        binding.dialogNormalRightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(final View view) {
                dismiss();
                onClickCancel();
            }
        });
    }

    protected abstract void onClickEnsure();

    protected abstract void onClickCancel();
}