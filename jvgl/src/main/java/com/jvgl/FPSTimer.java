package com.jvgl;

import android.os.SystemClock;
import android.util.Log;

/**
 * Created by Norad on 20/07/2014.
 */

public class FPSTimer {
    public static double NANO_TO_MILLI = 1.0 / 1000000.0;
    public static double MILLI_TO_NANO = 1000000.0;

    private long m_startTime;
    private long m_lastTime;
    private long m_currentTime;
    private long m_endTime;
    private long m_frameTimes = 0;
    private short m_frames = 0;
    private float m_avgMSPS = 0;

    private short VBCT = 0;

    /** Start counting the fps**/
    public void startCounting(){
        m_startTime = SystemClock.uptimeMillis();
    }

    public void set_verbosity(int _set){VBCT = (short)_set;}
    /**stop counting the fps and display it at the console*/
    public void stopAndPost(){
        //get the current time
        m_endTime = SystemClock.uptimeMillis();
        m_currentTime = SystemClock.uptimeMillis();
        //the difference between start and end times
        m_frameTimes = m_endTime - m_startTime;

        m_avgMSPS += m_currentTime - m_lastTime;
        m_avgMSPS /= 2;
        m_lastTime = m_currentTime;

        //count one frame
        ++m_frames;
        //if the difference is greater than 1 second (or 1000ms) post the results
        if(m_frameTimes >= 1000){
            //post results at the console
            if (VBCT >= 1){
                Log.d("", "FPS : "+Long.toString(m_frames) + " | MsPU : " + Float.toString(m_avgMSPS));
            }
            //reset time differences and number of counted m_frames
            m_frames = 0;
            m_frameTimes = 0;
            m_startTime = m_currentTime;
            }
    }
}
