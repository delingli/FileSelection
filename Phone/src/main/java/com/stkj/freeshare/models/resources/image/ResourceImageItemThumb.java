package com.stkj.freeshare.models.resources.image;

import android.app.Application;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.aigestudio.avatar.utils.LogUtil;
import com.bumptech.glide.Glide;
import com.stkj.freeshare.R;
import com.stkj.freeshare.framework.entities.resources.FSAlbum;

import java.io.File;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractSectionableItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * @author GAM
 * @since 2018-05-15
 */
public final class ResourceImageItemThumb extends AbstractSectionableItem<
        ResourceImageItemThumb.ViewHolder, ResourceImageItemHeader> {
    private final Application APPLICATION;
    final FSAlbum.FSImage IMAGE;

    ResourceImageItemThumb(ResourceImageItemHeader header, Application application,
                           FSAlbum.FSImage image) {
        super(header);
        APPLICATION = application;
        IMAGE = image;
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public int hashCode() {
        return IMAGE.path.hashCode();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_resource_image_thumb;
    }

    @Override
    public ViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public void bindViewHolder(final FlexibleAdapter<IFlexible> adapter, ViewHolder holder, int position, List<Object> payloads) {
        Glide.with(APPLICATION).load(new File(IMAGE.path)).into(holder.TV_THUMB);
        holder.CB_Check.setOnCheckedChangeListener(null);
        holder.CB_Check.setChecked(IMAGE.isChecked);
        holder.CB_Check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                IMAGE.isChecked = isChecked;
                if (isChecked) {
                    getHeader().ALBUM.count++;
                } else {
                    getHeader().ALBUM.count--;
                }
                adapter.updateItem(getHeader());
            }
        });
    }

    final class ViewHolder extends FlexibleViewHolder {
        private final ImageView TV_THUMB;
        private final CheckBox CB_Check;

        private ViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            TV_THUMB = view.findViewById(R.id.res_image_item_thumb_iv);
            CB_Check = view.findViewById(R.id.res_image_item_cb);
        }
    }
}