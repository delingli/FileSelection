package com.stkj.freeshare.fragments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stkj.freeshare.R;
import com.stkj.freeshare.framework.entities.resources.FileImpl;
import com.stkj.freeshare.framework.utils.FileSizeUtil;
import com.stkj.freeshare.framework.utils.FileUtils;
import com.stkj.freeshare.models.resources.files.DevicesStorageItem;
import com.stkj.freeshare.models.resources.files.ResourceFileNavEntiry;
import com.stkj.freeshare.models.resources.files.ResourceFilesItem;
import com.stkj.freeshare.models.resources.files.ResourceFilesNavAdapter;
import com.stkj.freeshare.models.resources.files.ResourceFilesStorageAdapter;
import com.stkj.freeshare.models.resources.files.ResourceFilesViewModelFractory;
import com.stkj.freeshare.models.resources.files.ResourceFliesViewModel;

import com.stkj.freeshare.utils.StorageBean;
import com.stkj.freeshare.utils.StorageUtils;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.common.SmoothScrollLinearLayoutManager;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;

public class ResourceFileFragment extends BaseFragment implements FlexibleAdapter.OnItemClickListener, Observer<List<AbstractFlexibleItem>> {
    private FlexibleAdapter<AbstractFlexibleItem> mAdapter;

    private LinearLayout ll_fileMain, ll_topNav;
    private RecyclerView rec_nav, mRecycleViewStoragList;
    private RecyclerView mRecyclerView;
    private ResourceFliesViewModel mViewModel;
    private ResourceFilesNavAdapter resourceFilesNavAdapter;
    private LinearLayout ll_content;
    private String mRootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    private List<String> mSelectItem = new ArrayList<String>();
    private List<DevicesStorageItem> DevicesStorages = new ArrayList<>();
    private ResourceFilesStorageAdapter mStorageAdapter;
    private List<ResourceFileNavEntiry> mNavFiles;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_files_resource, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycleViewStoragList = view.findViewById(R.id.recycleViewStoragList);
        ll_topNav = view.findViewById(R.id.ll_topNav);
        ll_fileMain = view.findViewById(R.id.ll_fileMain);
        ll_content = view.findViewById(R.id.ll_content);
        rec_nav = view.findViewById(R.id.rec_nav);
        mRecyclerView = view.findViewById(R.id.rec_filelist);
        ll_fileMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ll_content.getVisibility() == View.VISIBLE) {
                    ll_content.setVisibility(View.GONE);
                }
                mRecycleViewStoragList.setVisibility(View.VISIBLE);
                resent();
                initStrageData();
                initStrageListener();
            }
        });

    }

    /**
     * 初始化存储设备列表数据
     */
    private void initStrageData() {
        ArrayList<StorageBean> storageData = StorageUtils.getStorageData(getActivity().getApplicationContext());
        if (storageData != null) {
            DevicesStorageItem devicesstorageitem = null;
            DevicesStorages.clear();
            for (StorageBean storagebean : storageData) {
                //是否可移除，是则是外置存储，USB或SD卡
                if (storagebean != null && storagebean.getRemovable()) {
                    //是否已挂载
                    if (storagebean.getMounted().equalsIgnoreCase("mounted")) {
                        devicesstorageitem = new DevicesStorageItem();
                        devicesstorageitem.setAvailableStorageDeviceSize(storagebean.getAvailableSize() + "");
                        devicesstorageitem.setStorageDeviceName("外部存储设备");
                        devicesstorageitem.setTatalStorageDeviceSize(storagebean.getTotalSize() + "");
                        devicesstorageitem.setPath(storagebean.getPath());
                        DevicesStorages.add(devicesstorageitem);
                        mRootPath = storagebean.getPath();
                    }
                } else {
                    //内部存储
                    if (storagebean.getMounted().equalsIgnoreCase("mounted")) {
                        devicesstorageitem = new DevicesStorageItem();
                        devicesstorageitem.setAvailableStorageDeviceSize(storagebean.getAvailableSize() + "");
                        devicesstorageitem.setStorageDeviceName("内部存储设备");
                        devicesstorageitem.setTatalStorageDeviceSize(storagebean.getTotalSize() + "");
                        devicesstorageitem.setPath(storagebean.getPath());
                        mRootPath = storagebean.getPath();
                        DevicesStorages.add(devicesstorageitem);
                    }
                }

            }
            mRecycleViewStoragList.setLayoutManager(new SmoothScrollLinearLayoutManager(getActivity()));
            mRecycleViewStoragList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
            mRecycleViewStoragList.setAdapter(mStorageAdapter = new ResourceFilesStorageAdapter(DevicesStorages));
        }


    }

    private void initStrageListener() {
        mStorageAdapter.addOnItemClickListener(new ResourceFilesStorageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (ll_content.getVisibility() == View.GONE) {
                    ll_content.setVisibility(View.VISIBLE);
                }
                mRecycleViewStoragList.setVisibility(View.GONE);
                DevicesStorageItem devicesStorageItem = DevicesStorages.get(position);
                if (null != devicesStorageItem) {
                    mViewModel.FeatchFileByPath(devicesStorageItem.getPath());
                    initNav(devicesStorageItem.getStorageDeviceName(), devicesStorageItem.getPath());
                }
            }
        });
    }

    private void resent() {
        if (mNavFiles != null) {
            mNavFiles.clear();
            resourceFilesNavAdapter.notifyDataSetChanged();
        }
    }

    private void initNav(String device, String path) {
        mNavFiles = new ArrayList<ResourceFileNavEntiry>();
        LinearLayoutManager linearLayoutManager = new SmoothScrollLinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rec_nav.setLayoutManager(linearLayoutManager);
        mRootPath = path;
        ResourceFileNavEntiry headNav = new ResourceFileNavEntiry(device, path);
        mNavFiles.add(headNav);
        resourceFilesNavAdapter = new ResourceFilesNavAdapter(mNavFiles);
        rec_nav.setAdapter(resourceFilesNavAdapter);
        resourceFilesNavAdapter.addOnItemClickListener(new ResourceFilesNavAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ResourceFileNavEntiry resourceFileNavEntiry = mNavFiles.get(position);
                if (resourceFileNavEntiry != null) {

                    resourceFilesNavAdapter.updateSet(resourceFileNavEntiry);
                    mViewModel.FeatchFileByPath(resourceFileNavEntiry.getFilePath());
                    rec_nav.smoothScrollToPosition(position);
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initStrageData();
        initStrageListener();
        initRecycleView();
    }

    public static BaseFragment newInstance() {
        ResourceFileFragment fragment = new ResourceFileFragment();
        return fragment;
    }

    private void initRecycleView() {
        mRecyclerView.setLayoutManager(new SmoothScrollLinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter = new FlexibleAdapter<>(null));
        mAdapter.addListener(this);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        final ResourceFilesViewModelFractory factory =
                new ResourceFilesViewModelFractory(getActivity().getApplication());
        mViewModel = ViewModelProviders.of(this, factory)
                .get(ResourceFliesViewModel.class);
        mViewModel.getLiveItems().observe(this, this);
        mViewModel.loadSource(1);
    }

    @Override
    public boolean onItemClick(View view, int position) {
        AbstractFlexibleItem abstractFlexibleItem = mViewModel.getLiveItems().getValue().get(position);
        if (abstractFlexibleItem instanceof ResourceFilesItem) {
            ResourceFilesItem item = (ResourceFilesItem) abstractFlexibleItem;
            if (item.getFileiml().isDirectory()) {
                mViewModel.FeatchFileByPath(item.getFileiml().getFilePath());
                ResourceFileNavEntiry resourceFileNavEntiry = new ResourceFileNavEntiry(item.getFileiml().getFileName(), item.getFileiml().getFilePath());
                resourceFilesNavAdapter.updateSet(resourceFileNavEntiry);
                rec_nav.smoothScrollToPosition(resourceFilesNavAdapter.getItemCount() - 1);

            } else {
                if (!item.getFileiml().isSelect()) {
                    mSelectItem.add(item.getFileiml().getFilePath());
                } else {
                    mSelectItem.remove(item.getFileiml().getFilePath());
                }

                item.getFileiml().setSelect(!item.getFileiml().isSelect());

                mAdapter.notifyDataSetChanged();
            }
        }
        return false;
    }

    @Override
    public void onChanged(@Nullable List<AbstractFlexibleItem> abstractFlexibleItems) {
        if (mSelectItem != null && mSelectItem.size() != 0) {
            for (String itemPath : mSelectItem) {
                for (AbstractFlexibleItem it : abstractFlexibleItems) {
                    ResourceFilesItem newItem = (ResourceFilesItem) it;
                    if (newItem != null && newItem.getFileiml() != null && newItem.getFileiml().getFilePath() != null && newItem.getFileiml().getFilePath().equals(itemPath)) {
                        newItem.getFileiml().setSelect(true);
                    }
                }
            }
        }
        mAdapter.updateDataSet(abstractFlexibleItems);
    }
}
