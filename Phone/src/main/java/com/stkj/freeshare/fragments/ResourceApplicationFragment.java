package com.stkj.freeshare.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.stkj.freeshare.R;
import com.stkj.freeshare.framework.entities.resources.FSApplication;
import com.stkj.freeshare.models.resources.application.ResourceApplicationItemIcon;
import com.stkj.freeshare.models.resources.application.ResourceApplicationViewModel;
import com.stkj.freeshare.models.resources.application.ResourceApplicationViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;

/**
 * @author GAM
 * @since 2018-05-05
 */
public final class ResourceApplicationFragment extends ResourceFragment
        implements Observer<List<AbstractFlexibleItem>> {
    private FlexibleAdapter<AbstractFlexibleItem> mAdapter;
    private ResourceApplicationViewModel mViewModel;

//    public final List<FSApplication> getApplications() {
//        List<FSApplication> applications = new ArrayList<>();
//        for (AbstractFlexibleItem item : mViewModel.getLiveItems().getValue()) {
//            if (item.getItemViewType() == R.layout.fragment_resource_application_head) continue;
//            applications.add(((ResourceApplicationItemIcon) item).MODEL);
//        }
//        return applications;
//    }

    @Override
    final void onViewCreated(@NonNull final RecyclerView view,
                             @Nullable final Bundle savedInstanceState) {
        if (null == getContext()) {

            return;
        }
        final GridLayoutManager glm = new GridLayoutManager(getContext(), 4);
        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public final int getSpanSize(int position) {
                if (position == 0) return 4;
                return 1;
            }
        });
        view.setLayoutManager(glm);
        view.setAdapter(mAdapter = new FlexibleAdapter<>(null));
    }

    @Override
    public final void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (null == getActivity() || null == getActivity().getApplication()) {

            return;
        }
        final ResourceApplicationViewModelFactory factory =
                new ResourceApplicationViewModelFactory(getActivity().getApplication());
        mViewModel = ViewModelProviders.of(this, factory)
                .get(ResourceApplicationViewModel.class);
        mViewModel.getLiveItems().observe(this, this);
        mViewModel.loadSource(1);
    }

    @Override
    public final void onChanged(@Nullable final List<AbstractFlexibleItem> items) {
        mAdapter.updateDataSet(items);
    }
}