package com.stkj.freeshare.models.resources.files;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


import com.stkj.freeshare.R;
import com.stkj.freeshare.databinding.FragmentResourceApplicationHeadBinding;
import com.stkj.freeshare.framework.entities.resources.FileImpl;
import com.stkj.freeshare.framework.utils.FileUtils;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.FlexibleViewHolder;

public class ResourceFilesItem extends AbstractFlexibleItem<RecyclerView.ViewHolder> {
    private FileImpl fileiml;
    private static final int TYPE_DIRECTORY = 1;
    private static final int TYPE_FILE = 2;
    private static final int  VIEW_TYPE_EMPTY_LIST_PLACEHOLDER=4;
    public FileImpl getFileiml() {
        return fileiml;
    }

    public void setFileiml(FileImpl fileiml) {
        this.fileiml = fileiml;
    }

    @Override
    public int getItemViewType() {
        if(fileiml.isNull()){
            return VIEW_TYPE_EMPTY_LIST_PLACEHOLDER;
        }
        if (fileiml.isDirectory()) {
            return TYPE_DIRECTORY;
        } else {
            return TYPE_FILE;
        }
    }

    public ResourceFilesItem(FileImpl fileiml) {
        this.fileiml = fileiml;
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public int getLayoutRes() {
        if(fileiml.isNull()){
            return R.layout.fragment_resource_empty;
        }
        if (fileiml.isDirectory()) {
            return R.layout.select_item_filefold;
        } else {
            return R.layout.select_item_file;
        }
    }

    @Override
    public RecyclerView.ViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter) {
        int itemViewType = getItemViewType();
        switch (itemViewType) {
            case TYPE_DIRECTORY:
                return new ResourceFilesFloadItemHolder(view, adapter);
            case TYPE_FILE:
                return new ResourceFilesItemHolder(view, adapter);
            case VIEW_TYPE_EMPTY_LIST_PLACEHOLDER:
                return new ResourceFilesItemEmptyHolder(view,adapter);
        }
        return new ResourceFilesFloadItemHolder(view, adapter);
    }


    @Override
    public void bindViewHolder(FlexibleAdapter<IFlexible> adapter, RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        //设置数据
        if (holder instanceof ResourceFilesItemHolder) {
            ResourceFilesItemHolder resourcefilesitemholder = (ResourceFilesItemHolder) holder;
            if(fileiml.isSelect()){
                resourcefilesitemholder.iv_select.setImageResource(R.drawable.ic_res_checked);
            }else{
                resourcefilesitemholder.iv_select.setImageResource(R.drawable.ic_res_uncheck);
            }
            resourcefilesitemholder.tv_filename.setText(fileiml.getFileName());
            resourcefilesitemholder.tv_filesize.setText(fileiml.getSize());
            resourcefilesitemholder.tv_filetime.setText(fileiml.getModifyTime());
            if(fileiml.getFileType()==FileUtils.TYPE_APK){
                resourcefilesitemholder.iv_img.setImageDrawable(FileUtils.getApkDrawableInfo(fileiml.getFilePath(),  resourcefilesitemholder.iv_img.getContext().getApplicationContext()));
            }else{
                resourcefilesitemholder.iv_img.setImageResource(getFileIconByPath(fileiml.getFileType()));
            }

        } else if (holder instanceof ResourceFilesFloadItemHolder) {
            ResourceFilesFloadItemHolder resourcefilesfloaditemholder = (ResourceFilesFloadItemHolder) holder;
            resourcefilesfloaditemholder.tv_floadName.setText(fileiml.getFileName());
            resourcefilesfloaditemholder.iv_img.setImageResource(R.drawable.ic_res_folder);

        }else{
            //空布局

        }
    }

    /**
     * 通过文件名获取文件图标
     */
    public static int getFileIconByPath(int filetype) {
        int iconId = R.drawable.ic_transfer_unknow;
        switch (filetype) {
            case FileUtils.TYPE_NORMAL:
                iconId=R.drawable.ic_transfer_unknow;
                break;
            case FileUtils.TYPE_APK:
                break;
            case FileUtils.TYPE_TXT:
                iconId = R.drawable.ic_transfer_txt;
                break;
            case FileUtils.TYPE_DOC:
                iconId = R.drawable.ic_transfer_doc;
                break;
            case FileUtils.TYPE_XLS:
                iconId = R.drawable.ic_transfer_xlsx;
                break;
            case FileUtils.TYPE_VIDEO:
                iconId = R.drawable.ic_transfer_music;
                break;
            case FileUtils.TYPE_JPG:
                iconId = R.drawable.ic_transfer_image;
                break;
            case FileUtils.TYPE_PPT:
                iconId = R.drawable.ic_transfer_ppt;
                break;
        }
        return iconId;
    }
    static final class ResourceFilesItemHolder extends FlexibleViewHolder {

        private final ImageView iv_img;
        private TextView tv_filename, tv_filetime, tv_filesize;
        private ImageView iv_select;

        private ResourceFilesItemHolder(final View view, final FlexibleAdapter adapter) {
            super(view, adapter);
            iv_img = view.findViewById(R.id.iv_img);
            tv_filename = view.findViewById(R.id.tv_filename);
            tv_filetime = view.findViewById(R.id.tv_filetime);
            tv_filesize = view.findViewById(R.id.tv_filesize);
            iv_select = view.findViewById(R.id.iv_select);


        }
    }
    static final class ResourceFilesItemEmptyHolder extends FlexibleViewHolder {

        private ResourceFilesItemEmptyHolder(final View view, final FlexibleAdapter adapter) {
            super(view, adapter);

        }
    }
    static final class ResourceFilesFloadItemHolder extends FlexibleViewHolder {
        private ImageView iv_img;
        private TextView tv_floadName;

        private ResourceFilesFloadItemHolder(final View view, final FlexibleAdapter adapter) {
            super(view, adapter);
            iv_img = view.findViewById(R.id.iv_img);
            tv_floadName = view.findViewById(R.id.tv_floadName);
        }
    }
}
