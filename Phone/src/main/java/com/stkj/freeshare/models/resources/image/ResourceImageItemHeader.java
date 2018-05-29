package com.stkj.freeshare.models.resources.image;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.stkj.freeshare.R;
import com.stkj.freeshare.framework.entities.resources.FSAlbum;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractExpandableHeaderItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.ExpandableViewHolder;

/**
 * @author GAM
 * @since 2018-05-15
 */
public final class ResourceImageItemHeader extends AbstractExpandableHeaderItem<
        ResourceImageItemHeader.ViewHolder, ResourceImageItemThumb> {
    final FSAlbum ALBUM;

    ResourceImageItemHeader(final FSAlbum album) {
        ALBUM = album;
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public int hashCode() {
        return ALBUM.path.hashCode();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_resource_image_header;
    }

    @Override
    public ViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public void bindViewHolder(final FlexibleAdapter<IFlexible> adapter, ViewHolder holder, int position,
                               List<Object> payloads) {
//        ALBUM.isChecked = ALBUM.count == ALBUM.images.size();
//        if (null != holder.BINDING) holder.BINDING.setAlbum(ALBUM);
        holder.TV_TITLE.setText(ALBUM.path);
        holder.CB_ALL.setOnCheckedChangeListener(null);
        holder.CB_ALL.setChecked(ALBUM.count == ALBUM.images.size());
        holder.CB_ALL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ALBUM.count = ALBUM.images.size();
                } else {
                    ALBUM.count = 0;
                }
                for (ResourceImageItemThumb thumb : mSubItems) {
                    thumb.IMAGE.isChecked = isChecked;
                    adapter.updateItem(thumb);
                }
            }
        });
    }

    final class ViewHolder extends ExpandableViewHolder {
        private final CheckBox CB_ALL;
        private final TextView TV_TITLE;
//        private final FragmentResourceImageHeaderBinding BINDING;

        private ViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter, true);
//            BINDING = DataBindingUtil.bind(view);
//            BINDING.resImageItemHeaderAllCb.setOnCheckedChangeListener(this);
            TV_TITLE = view.findViewById(R.id.res_image_item_header_title_tv);
            CB_ALL = view.findViewById(R.id.res_image_item_header_all_cb);
        }
    }
}