package com.stkj.freeshare.models.resources.application;

import android.app.Application;
import android.databinding.DataBindingUtil;
import android.view.View;

import com.stkj.freeshare.R;
import com.stkj.freeshare.databinding.FragmentResourceApplicationHeadBinding;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * @author GAM
 * @since 2018-05-06
 */
public final class ResourceApplicationItemHead extends
        AbstractFlexibleItem<ResourceApplicationItemHead.HeadViewHolder> {
    private final Application APPLICATION;
    private final int COUNT;

    ResourceApplicationItemHead(final Application application, final int count) {
        APPLICATION = application;
        COUNT = count;
    }

    @Override
    public final boolean equals(final Object o) {
        return this == o;
    }

    @Override
    public final int getLayoutRes() {
        return R.layout.fragment_resource_application_head;
    }

    @Override
    public final HeadViewHolder createViewHolder(final View view,
                                                 final FlexibleAdapter<IFlexible> adapter) {
        return new HeadViewHolder(view, adapter);
    }

    @Override
    public final void bindViewHolder(final FlexibleAdapter<IFlexible> adapter,
                                     final HeadViewHolder holder, final int position,
                                     final List<Object> payloads) {
        holder.BINDING.setTitle(APPLICATION.getString(R.string.resource_application_title, COUNT));
    }

    static final class HeadViewHolder extends FlexibleViewHolder {
        private final FragmentResourceApplicationHeadBinding BINDING;

        private HeadViewHolder(final View view, final FlexibleAdapter adapter) {
            super(view, adapter);
            BINDING = DataBindingUtil.bind(view);
        }
    }
}