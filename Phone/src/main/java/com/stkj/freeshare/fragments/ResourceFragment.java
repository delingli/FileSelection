package com.stkj.freeshare.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stkj.freeshare.R;
import com.stkj.freeshare.databinding.FragmentResourceBinding;

/**
 * @author AigeStudio
 * @since 2018-05-06
 */
abstract class ResourceFragment extends BaseFragment {
    private FragmentResourceBinding mBinding;

    @NonNull
    @Override
    public final View onCreateView(@NonNull final LayoutInflater inflater,
                                   @Nullable final ViewGroup container,
                                   @Nullable final Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_resource, container, false);
        return mBinding.getRoot();
    }

    @Override
    public final void onViewCreated(@NonNull final View view,
                                    @Nullable final Bundle savedInstanceState) {
        onViewCreated(mBinding.resContentRv, savedInstanceState);
    }

    abstract void onViewCreated(@NonNull final RecyclerView view,
                                @Nullable final Bundle savedInstanceState);
}