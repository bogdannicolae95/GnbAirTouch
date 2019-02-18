package com.example.gnbproject;

import android.app.Application;
import android.support.annotation.StringRes;
import android.widget.Toast;

public class GnbApp extends Application {

    protected static GnbApp currentApp;

    public GnbApp(){
        currentApp = this;
    }

    public static GnbApp getCurrentApp(){
        return currentApp;
    }

    public static String getStringRes(@StringRes int stringRes){
        return currentApp.getString(stringRes);
    }

    public static void notifyWithToast(String message,int duration){
        Toast.makeText(getCurrentApp(),message,duration).show();
    }
}
