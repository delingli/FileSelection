package com.stkj.freeshare.models.resources.files;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stkj.freeshare.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ResourceFilesNavAdapter extends RecyclerView.Adapter<ResourceFilesNavAdapter.ResourceFilesNavViewHolder> {
    private List<ResourceFileNavEntiry> mData;

    public ResourceFilesNavAdapter(List<ResourceFileNavEntiry> mData) {
        this.mData = mData;
    }
    public void updateSet(ResourceFileNavEntiry data){
        if(mData==null){
            mData=new ArrayList<>();
        }
        if(mData.contains(data)){
            //删除
            List<ResourceFileNavEntiry> list=new ArrayList<>();
            for(ResourceFileNavEntiry item:mData){
                list.add(item);
                if(item.equals(data)){
                   break;
                }
            }
            mData.clear();
            mData.addAll(list);
        }else{
            mData.add(data);
        }
        notifyDataSetChanged();
    }
    private OnItemClickListener onItemClickListener;
public void addOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
}
  public   interface OnItemClickListener{
        void onItemClick(int position);
    }
    @NonNull
    @Override
    public ResourceFilesNavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nav_resource, parent, false);
        return new ResourceFilesNavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResourceFilesNavViewHolder holder, final int position) {
        ResourceFileNavEntiry resourceFileNavEntiry = mData.get(position);
        if(resourceFileNavEntiry!=null){
            holder.tv_filefloaderName.setText(resourceFileNavEntiry.getFileFloadName());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null!=onItemClickListener){
                    onItemClickListener.onItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }

     static class ResourceFilesNavViewHolder extends RecyclerView.ViewHolder{
        TextView tv_filefloaderName ;
         View itemView;
        public ResourceFilesNavViewHolder(View itemView ) {
            super(itemView);
            this.itemView=itemView;
            tv_filefloaderName=  itemView.findViewById(R.id.tv_filefloaderName);

        }

    }
}
