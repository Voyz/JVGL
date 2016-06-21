package com.jvgl;

import android.util.Log;

/**
 * Created by Norad on 05/08/2014.
 */
public class Logger {

    public Logger(){

    }
    public static void d(int _verbosity, int _parentVerbosity, String _tag, String _msg){
        if (_verbosity <= _parentVerbosity){
            Log.d(_tag, _msg);
        }
    }
}
