package com.stkj.freeshare.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.wifi.p2p.WifiP2pDevice;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aigestudio.needle.wifidirect.IWIFIDirectServer;
import com.aigestudio.needle.wifidirect.OnWIFIDirectClientChangeListener;
import com.aigestudio.needle.wifidirect.OnWIFIDirectCurrentDeviceChangeListener;
import com.aigestudio.needle.wifidirect.OnWIFIDirectDeviceChangeListener;
import com.aigestudio.needle.wifidirect.WIFIDirectClientInfo;
import com.aigestudio.needle.wifidirect.WIFIDirectServerService;
import com.stkj.freeshare.R;
import com.stkj.freeshare.views.RadarSurfaceView;
import com.ventsea.nio.message.DeviceInfo;
import com.ventsea.nio.message.FileBean;
import com.ventsea.nio.server.DCClient;
import com.ventsea.nio.server.DCServer;
import com.ventsea.nio.server.DownloadHandler;
import com.ventsea.nio.server.IServerListener;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.FlexibleViewHolder;

public final class SendActivity extends TitleBarActivity implements
        OnWIFIDirectCurrentDeviceChangeListener, OnWIFIDirectDeviceChangeListener,
        OnWIFIDirectClientChangeListener, FlexibleAdapter.OnItemClickListener, IServerListener {
    private final ServiceConnection CONNECTION = new ServiceConnection() {
        @Override
        public final void onServiceConnected(final ComponentName name, final IBinder service) {
            WIFIDirectServerService.ServerBinder binder =
                    (WIFIDirectServerService.ServerBinder) service;
            mWIFIDirect = binder.getWIFIDirect();
            mWIFIDirect.setOnWIFIDirectCurrentDeviceChangeListener(SendActivity.this);
            mWIFIDirect.setOnWIFIDirectDeviceChangeListener(SendActivity.this);
            mWIFIDirect.setOnWIFIDirectClientChangeListener(SendActivity.this);
            WifiP2pDevice device = mWIFIDirect.getCurrentDevice();
            if (null != device) onCurrentDeviceChanged(device);
            mWIFIDirect.startSearchDevice();
        }

        @Override
        public final void onServiceDisconnected(final ComponentName name) {
            mWIFIDirect.setOnWIFIDirectCurrentDeviceChangeListener(null);
            mWIFIDirect.setOnWIFIDirectDeviceChangeListener(null);
            mWIFIDirect.setOnWIFIDirectClientChangeListener(null);
        }
    };
    private IWIFIDirectServer mWIFIDirect;
    private FlexibleAdapter<AbstractFlexibleItem> mAdapter;
    private TextView mTVDevice;

    @Override
    protected final void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTVTitle.setText(R.string.title_send);
        setContentView(R.layout.activity_send);
        mTVDevice = findViewById(R.id.send_device_tv);
        RadarSurfaceView radar = findViewById(R.id.send_radar_sv);
        radar.setAlignView(mTVDevice);
        RecyclerView list = findViewById(R.id.send_list_rv);
        list.setLayoutManager(new GridLayoutManager(this, 2));
        list.setAdapter(mAdapter = new FlexibleAdapter<>(null, this));

        startService(new Intent(this, WIFIDirectServerService.class));
    }

    @Override
    protected final void onStart() {
        super.onStart();
        bindService(new Intent(this, WIFIDirectServerService.class), CONNECTION, BIND_AUTO_CREATE);
    }

    @Override
    protected final void onStop() {
        unbindService(CONNECTION);
        super.onStop();
    }

    @Override
    protected final void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, WIFIDirectServerService.class));
    }

    @Override
    public final void onCurrentDeviceChanged(final WifiP2pDevice device) {
        mTVDevice.setText(device.deviceName);
    }

    @Override
    public final void onDeviceChanged(final List<WifiP2pDevice> devices) {
        List<AbstractFlexibleItem> items = new ArrayList<>();
        for (WifiP2pDevice device : devices) {
            items.add(new DeviceItem(device));
        }
        mAdapter.updateDataSet(items);
    }

    @Override
    public final void onWIFIDirectClientChanged(final List<WIFIDirectClientInfo> clients) {
//        startActivity(new Intent(this, SuccessActivity.class));
        // Server
        DCServer.getInstance().onWifiConnect(null, clients.get(0).server.mac);
        DCServer.getInstance().setServerListener(this);
        DCServer.getInstance().start();

        // Client
        DCClient.getInstance().onWifiConnect("", clients.get(0).server.mac);
        DCClient.getInstance().setConnectListener(null);
        DCClient.getInstance().connect();
    }

    @Override
    public final boolean onItemClick(final View view, final int position) {
        return true;
    }

    @Override
    public void onServerStarted() {

    }

    @Override
    public void onHandShaker(String s) {
        DCServer.getInstance().sendFileListMessage(null, null);
    }

    @Override
    public void onServerStop() {

    }

    @Override
    public void onReceiverListMessage(DeviceInfo deviceInfo, List<FileBean> list) {
        //
        DownloadHandler.getInstance().addFileBean(null);
        DownloadHandler.getInstance().setDownloadListener(null);
        DownloadHandler.getInstance().startDownload();
    }

    private static final class DeviceItem extends AbstractFlexibleItem<DeviceItem.DeviceViewHolder> {
        private final WifiP2pDevice DEVICE;

        private DeviceItem(WifiP2pDevice device) {
            DEVICE = device;
        }

        @Override
        public boolean equals(Object o) {
            return this == o;
        }

        @Override
        public int getLayoutRes() {
            return R.layout.item_device;
        }

        @Override
        public DeviceViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter) {
            return new DeviceViewHolder(view, adapter);
        }

        @Override
        public void bindViewHolder(FlexibleAdapter<IFlexible> adapter, DeviceViewHolder holder, int position, List<Object> payloads) {
            holder.TV_DEVICE.setText(DEVICE.deviceName);
        }

        static final class DeviceViewHolder extends FlexibleViewHolder {
            private final TextView TV_DEVICE;

            private DeviceViewHolder(View view, FlexibleAdapter adapter) {
                super(view, adapter);
                TV_DEVICE = (TextView) view;
            }
        }
    }
}