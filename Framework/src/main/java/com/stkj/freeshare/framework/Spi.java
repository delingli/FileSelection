package com.stkj.freeshare.framework;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author AigeStudio
 * @since 2018-05-22
 */
public final class Spi {
    private static final String PREF_NEED_SHOW_PROTOCOL = "3F174150D44EA9D6";

    private volatile static Spi INSTANCE;

    private final SharedPreferences PREF;

    private Spi(Context context) {
        PREF = context.getSharedPreferences("7B7FE3608A12A71F", Context.MODE_PRIVATE);
    }

    public static Spi get(Context context) {
        if (null == INSTANCE) synchronized (Spi.class) {
            if (null == INSTANCE) INSTANCE = new Spi(context);
        }
        return INSTANCE;
    }

    public final boolean isNeedShowProtocol() {
        return PREF.getBoolean(PREF_NEED_SHOW_PROTOCOL, true);
    }

    public final void setNeedShowProtocol(boolean isNeedShowProtocol) {
        PREF.edit().putBoolean(PREF_NEED_SHOW_PROTOCOL, false).apply();
    }
}