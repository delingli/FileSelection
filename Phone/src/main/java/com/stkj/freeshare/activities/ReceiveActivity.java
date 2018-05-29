package com.stkj.freeshare.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.wifi.p2p.WifiP2pDevice;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.aigestudio.needle.wifidirect.IWIFIDirectClient;
import com.aigestudio.needle.wifidirect.OnWIFIDirectCurrentDeviceChangeListener;
import com.aigestudio.needle.wifidirect.OnWIFIDirectServerConnectListener;
import com.aigestudio.needle.wifidirect.WIFIDirectClientService;
import com.aigestudio.needle.wifidirect.WIFIDirectServerInfo;
import com.aigestudio.needle.wifidirect.WIFIDirectServerService;
import com.stkj.freeshare.R;
import com.stkj.freeshare.views.RadarSurfaceView;

public final class ReceiveActivity extends TitleBarActivity implements
        OnWIFIDirectCurrentDeviceChangeListener, OnWIFIDirectServerConnectListener {
    private final ServiceConnection CONNECTION = new ServiceConnection() {
        @Override
        public final void onServiceConnected(final ComponentName name, final IBinder service) {
            WIFIDirectClientService.ClientBinder binder =
                    (WIFIDirectClientService.ClientBinder) service;
            mWIFIDirect = binder.getWIFIDirect();
            mWIFIDirect.setOnWIFIDirectCurrentDeviceChangeListener(ReceiveActivity.this);
            mWIFIDirect.setOnWIFIDirectServerConnectListener(ReceiveActivity.this);
            WifiP2pDevice device = mWIFIDirect.getCurrentDevice();
            if (null != device) onCurrentDeviceChanged(device);
            mWIFIDirect.startSearchDevice();
        }

        @Override
        public final void onServiceDisconnected(final ComponentName name) {
            mWIFIDirect.setOnWIFIDirectCurrentDeviceChangeListener(null);
            mWIFIDirect.setOnWIFIDirectServerConnectListener(null);
        }
    };
    private IWIFIDirectClient mWIFIDirect;
    private TextView mTVDevice;

    @Override
    protected final void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTVTitle.setText(R.string.title_receive);
        setContentView(R.layout.activity_receive);
        mTVDevice = findViewById(R.id.receive_device_tv);
        RadarSurfaceView radar = findViewById(R.id.receive_radar_sv);
        radar.setAlignView(mTVDevice);

        startService(new Intent(this, WIFIDirectClientService.class));
    }

    @Override
    protected final void onStart() {
        super.onStart();
        bindService(new Intent(this, WIFIDirectClientService.class), CONNECTION, BIND_AUTO_CREATE);
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
    public final void onWIFIDirectServerConnected(final WIFIDirectServerInfo server) {
    }
}