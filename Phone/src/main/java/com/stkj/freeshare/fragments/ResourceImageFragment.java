package com.stkj.freeshare.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.stkj.freeshare.models.resources.image.ResourceImageItemHeader;
import com.stkj.freeshare.models.resources.image.ResourceImageViewModel;
import com.stkj.freeshare.models.resources.image.ResourceImageViewModelFactory;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFlexible;

/**
 * @author GAM
 * @since 2018-05-10
 */
public final class ResourceImageFragment extends ResourceFragment implements FlexibleAdapter.OnItemClickListener, Observer<List<AbstractFlexibleItem>> {
    private FlexibleAdapter<AbstractFlexibleItem> mAdapter;
    private ResourceImageViewModel mViewModel;

    @Override
    final void onViewCreated(@NonNull final RecyclerView view,
                             @Nullable final Bundle savedInstanceState) {
        final GridLayoutManager glm = new GridLayoutManager(getContext(), 3);
        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public final int getSpanSize(int position) {
                if (null == mAdapter) return 1;
                IFlexible flexible = mAdapter.getItem(position);
                if (flexible instanceof ResourceImageItemHeader) return 3;
                return 1;
            }
        });
        view.setLayoutManager(glm);
        view.setAdapter(mAdapter = new FlexibleAdapter<>(null));

        mAdapter.addListener(this);
        mAdapter.expandItemsAtStartUp()
                .setAnimateToLimit(Integer.MAX_VALUE);
    }

    @Override
    public final void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (null == getActivity() || null == getActivity().getApplication()) {

            return;
        }
        final ResourceImageViewModelFactory factory =
                new ResourceImageViewModelFactory(getActivity().getApplication());
        mViewModel = ViewModelProviders.of(this, factory)
                .get(ResourceImageViewModel.class);
        mViewModel.getLiveItems().observe(this, this);
        mViewModel.loadSource(1);
    }


    @Override
    public boolean onItemClick(View view, int position) {
//        IFlexible flexible = mAdapter.getItem(position);
//        if (null == flexible) return false;
//        mAdapter.toggleSelection(position);
//        if (flexible instanceof ResourceImageItemThumb) {
//            ResourceImageItemHeader header = ((ResourceImageItemThumb) flexible).getHeader();
//            if (mAdapter.isSelected(position)) {
////                header.ALBUM.count++;
//            } else {
////                header.ALBUM.count--;
//            }
//            mAdapter.updateItem(header);
//        }
//        mAdapter.updateItem((AbstractFlexibleItem) flexible);
        return false;
    }

    @Override
    public final void onChanged(@Nullable final List<AbstractFlexibleItem> abstractFlexibleItems) {
        mAdapter.updateDataSet(abstractFlexibleItems, true);
    }
}