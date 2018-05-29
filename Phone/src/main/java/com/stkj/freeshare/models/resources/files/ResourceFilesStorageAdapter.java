package com.stkj.freeshare.models.resources.files;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stkj.freeshare.R;

import java.util.ArrayList;
import java.util.List;

public class ResourceFilesStorageAdapter extends RecyclerView.Adapter<ResourceFilesStorageAdapter.ResourceFilesNavViewHolder> {
    private List<DevicesStorageItem> mData;

    public ResourceFilesStorageAdapter(List<DevicesStorageItem> mData) {
        this.mData = mData;
    }


    private OnItemClickListener onItemClickListener;

    public void addOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public ResourceFilesNavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview_storagelist, parent, false);
        return new ResourceFilesNavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResourceFilesNavViewHolder holder, final int position) {
        DevicesStorageItem devicesstorageitem = mData.get(position);
        if (devicesstorageitem != null) {
            holder.tv_debiceStorageDesc.setText(devicesstorageitem.getStorageDeviceName());
            holder.tv_storageSize.setText("总"+devicesstorageitem.getTatalStorageDeviceSize()+"/可用"+devicesstorageitem.getAvailableStorageDeviceSize());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onItemClickListener) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class ResourceFilesNavViewHolder extends RecyclerView.ViewHolder {
        TextView tv_debiceStorageDesc,tv_storageSize;
        View itemView;
        public ResourceFilesNavViewHolder(View itemView) {
            super(itemView);
           this.itemView=itemView;
            tv_debiceStorageDesc = itemView.findViewById(R.id.tv_debiceStorageDesc);
            tv_storageSize = itemView.findViewById(R.id.tv_storageSize);
        }

    }
}
