package com.stkj.freeshare.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.aigestudio.avatar.utils.SysUtil;
import com.stkj.freeshare.R;
import com.stkj.freeshare.dialogs.PermissionForbidCancel;
import com.stkj.freeshare.dialogs.PermissionGrantDialog;

import java.util.ArrayList;
import java.util.List;

abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "DB_PMS";

    protected final void toast(final String message) {
        Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    protected final void checkPermission(final int code, final String permission) {
        checkPermission(code, new String[]{permission});
    }

    protected final void checkPermission(final int code, final String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onPermissionsGranted(code);
            return;
        }
        List<String> unauthorized = null;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED)
                continue;
            if (null == unauthorized) unauthorized = new ArrayList<>();
            unauthorized.add(permission);
        }
        if (null == unauthorized) {
            onPermissionsGranted(code);
            return;
        }
        List<String> normalize = null;
        List<String> rationale = null;
        for (String permission : unauthorized) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                if (null == rationale) rationale = new ArrayList<>();
                rationale.add(permission);
            } else {
                if (null == normalize) normalize = new ArrayList<>();
                normalize.add(permission);
            }
        }
        unauthorized.clear();
        if (null != rationale) unauthorized.addAll(rationale);
        if (null != normalize) unauthorized.addAll(normalize);
        if (null != rationale) {
            final String[] authorize = unauthorized.toArray(new String[unauthorized.size()]);
            new PermissionGrantDialog(this, descPermission(authorize)) {
                @Override
                protected void onKnownClicked() {
                    ActivityCompat.requestPermissions(BaseActivity.this, authorize, code);
                }
            }.show();
        } else {
            ActivityCompat.requestPermissions(this, unauthorized.toArray
                    (new String[unauthorized.size()]), code);
        }
    }

    protected void onPermissionsGranted(int code) {

    }

    private String descPermission(String[] permissions) {
        StringBuilder sb = new StringBuilder();
        String separator = getResources().getString(R.string.permission_separator);
        String connector = getResources().getString(R.string.permission_connector);
        for (String permission : permissions) {
            switch (permission) {
                case Manifest.permission.ACCESS_COARSE_LOCATION:
                case Manifest.permission.ACCESS_FINE_LOCATION:
                    String location = getResources().getString(R.string.permission_location);
                    if (sb.indexOf(location) == -1) sb.append(location).append(separator);
                    break;
                case Manifest.permission.READ_PHONE_STATE:
                    String phone = getResources().getString(R.string.permission_phone_state);
                    if (sb.indexOf(phone) == -1) sb.append(phone).append(separator);
                    break;
                case Manifest.permission.READ_EXTERNAL_STORAGE:
                case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                    String storage = getResources().getString(R.string.permission_storage);
                    if (sb.indexOf(storage) == -1) sb.append(storage).append(separator);
                    break;
            }
        }
        if (sb.indexOf(separator) == sb.length() - 1) {
            sb.deleteCharAt(sb.length() - 1);
        } else {
            sb.deleteCharAt(sb.length() - 1);
            int index = sb.lastIndexOf(separator);
            sb.replace(index, index + 1, connector);
        }
        return sb.toString();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public final void onRequestPermissionsResult(final int code,
                                                 @NonNull final String[] permissions,
                                                 @NonNull final int[] results) {
        if (permissions.length == 0 || results.length == 0) {
            return;
        }
        if (permissions.length != results.length) {
            return;
        }
        List<String> unauthorized = null;
        for (int i = 0; i < permissions.length; i++) {
            if (results[i] == PackageManager.PERMISSION_GRANTED)
                continue;
            if (null == unauthorized)
                unauthorized = new ArrayList<>();
            unauthorized.add(permissions[i]);
        }
        if (null == unauthorized) {
            onPermissionsGranted(code);
            return;
        }
        List<String> forbid = null;
        List<String> refuse = null;
        for (String permission : unauthorized) {
            if (shouldShowRequestPermissionRationale(permission)) {
                if (null == refuse) refuse = new ArrayList<>();
                refuse.add(permission);
            } else {
                if (null == forbid) forbid = new ArrayList<>();
                forbid.add(permission);
            }
        }
        if (null != refuse) {
            checkPermission(code, refuse.toArray(new String[refuse.size()]));
            return;
        }
        if (null != forbid) {
            onPermissionsForbid(code, forbid.toArray(new String[forbid.size()]));
        }
    }

    private void onPermissionsForbid(int code, String[] permission) {
        showDiaPermissionForbid(code, descPermission(permission));
    }

    private void showDiaPermissionForbid(final int code, final String permission) {
        new PermissionForbidCancel(this, permission) {
            @Override
            protected void onClickEnsure() {
                SysUtil.openApplicationSettings(BaseActivity.this);
                finish();
            }

            @Override
            protected void onClickCancel() {
                onPermissionsForbidCancel(code, permission);
            }
        }.show();
    }

    protected void onPermissionsForbidCancel(int code, String permission) {
        toast(getString(R.string.permission_forbid_toast, permission));
        finish();
    }
}