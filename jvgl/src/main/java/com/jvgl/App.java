package com.jvgl;

import android.app.Application;
import android.content.Context;

/**
 * Created by Norad on 13/07/2014.
 */
public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}