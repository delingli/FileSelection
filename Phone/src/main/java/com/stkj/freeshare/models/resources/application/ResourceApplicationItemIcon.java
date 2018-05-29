package com.stkj.freeshare.models.resources.application;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.view.View;

import com.stkj.freeshare.R;
import com.stkj.freeshare.databinding.FragmentResourceApplicationIconBinding;
import com.stkj.freeshare.framework.entities.resources.FSApplication;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * @author GAM
 * @since 2018-05-06
 */
public final class ResourceApplicationItemIcon extends AbstractFlexibleItem<ResourceApplicationItemIcon.IconViewHolder> {
    public final FSApplication MODEL;

    ResourceApplicationItemIcon(final FSApplication model) {
        MODEL = model;
    }

    @Override
    public final boolean equals(final Object o) {
        return this == o;
    }

    @Override
    public final int getLayoutRes() {
        return R.layout.fragment_resource_application_icon;
    }

    @Override
    public final IconViewHolder createViewHolder(final View view,
                                                 final FlexibleAdapter<IFlexible> adapter) {
        return new IconViewHolder(view, adapter);
    }

    @Override
    public final void bindViewHolder(final FlexibleAdapter<IFlexible> adapter,
                                     final IconViewHolder holder, final int position,
                                     final List<Object> payloads) {
        if (null == holder.BINDING) return;
        holder.BINDING.setApplication(MODEL);
    }

    static final class IconViewHolder extends FlexibleViewHolder {
        @Nullable
        private final FragmentResourceApplicationIconBinding BINDING;

        private IconViewHolder(final View view, final FlexibleAdapter adapter) {
            super(view, adapter);
            BINDING = DataBindingUtil.bind(view);
        }
    }
}