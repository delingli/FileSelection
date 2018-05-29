package com.stkj.freeshare.models.resources.application;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.aigestudio.avatar.utils.LogUtil;
import com.stkj.freeshare.framework.entities.resources.FSApplication;

/**
 * @author GAM
 * @since 2018-05-10
 */
public final class ResourceApplicationEventHandler {
    @BindingAdapter("icon")
    public static void setIcon(ImageView view, FSApplication application) {
        if (null == view || null == view.getContext() ||
                null == view.getContext().getPackageManager() || null == application) {
            LogUtil.e("DB_DB_RES_APP", "设置应用图标前相关资源为空");
            return;
        }
        PackageManager pm = view.getContext().getPackageManager();
        ApplicationInfo info = getApplicationInfo(pm, application);
        if (null == info) {
            LogUtil.e("DB_DB_RES_APP", "设置应用图标时获取到空的应用信息");
            return;
        }
        view.setImageDrawable(pm.getApplicationIcon(info));
    }

    private static ApplicationInfo getApplicationInfo(PackageManager pm, FSApplication application) {
        ApplicationInfo info = null;
        if (!TextUtils.isEmpty(application.pkg)) {
            try {
                info = pm.getApplicationInfo(application.pkg, 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        } else if (!TextUtils.isEmpty(application.path)) {
            PackageInfo pi = pm.getPackageArchiveInfo(application.path, 0);
            if (null != pi) info = pi.applicationInfo;
        }
        return info;
    }

    @BindingAdapter("label")
    public static void setLabel(TextView view, FSApplication application) {
        if (null == view || null == view.getContext() ||
                null == view.getContext().getPackageManager() || null == application) {
            LogUtil.e("DB_DB_RES_APP", "设置应用标题前相关资源为空");
            return;
        }
        PackageManager pm = view.getContext().getPackageManager();
        ApplicationInfo info = getApplicationInfo(pm, application);
        if (null == info) {
            LogUtil.e("DB_DB_RES_APP", "设置应用标题时获取到空的应用信息");
            return;
        }
        view.setText(pm.getApplicationLabel(info));
    }
}