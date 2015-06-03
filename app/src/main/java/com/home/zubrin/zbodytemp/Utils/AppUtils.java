package com.home.zubrin.zbodytemp.Utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by zubrin on 6/3/15.
 */
public class AppUtils extends Application {

    // Variables

    private static Context sContext;

    // Lifecycle

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = getApplicationContext();
    }

    // Getters & setters

    public static Context getContext() {
        return sContext;
    }
}
