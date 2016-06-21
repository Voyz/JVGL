package com.jvgl;

import android.opengl.GLSurfaceView;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;

import com.jvgl.Norad.NActivity;

import java.util.ArrayList;

/**
 * Created by Norad on 05/08/2014.
 */
public class Core implements Runnable{
    private final static String TAG = new String("JVGL.Core ");
    public static int VBCT = 0;

    protected long m_period = 1000/60; // fps
    protected long m_startTime;
    protected long m_elapsedTime;
    protected long m_modTime;
    protected long m_currentTime;
    protected boolean m_running = true;
    protected boolean m_paused = false;
    protected Object m_pauseLock;
    protected Object m_logicLock;
    protected Object m_motionEventLock;

    protected final NActivity m_parentActivity;
    protected final GLSurfaceView m_parentView;
    protected FPSTimer m_fpsTimer;

    protected MotionEventQueue m_motionEventQueue;
    protected MotionEvent m_lastMotionEvent;

    protected updateMode m_updateMode;
    protected boolean m_updateDirty;

    public enum updateMode {UPDATE_WHEN_DIRTY, UPDATE_CONTINUOUSLY };

//    public Core(){
//        m_pauseLock = new Object();
//        m_fpsTimer = new FPSTimer();
//        m_parentActivity = null;
//        m_parentView = null;
//    }
    public Core(NActivity _parentActivity, GLSurfaceView _parentView){
        m_parentActivity = _parentActivity;
        m_parentView = _parentView;
        m_pauseLock = new Object();
        m_logicLock = new Object();
        m_motionEventLock = new Object();
        m_fpsTimer = new FPSTimer();
        m_updateMode = updateMode.UPDATE_CONTINUOUSLY;
        m_updateDirty = true;
        m_motionEventQueue = new MotionEventQueue();

        /// !!! DON'T SET UP ANY OPENGL HERE !!!
    }

    public synchronized void setup(){
    }

    protected synchronized void doLogic() throws InterruptedException {
    }

    @Override
    public void run() {
        m_startTime = SystemClock.uptimeMillis();
        m_modTime = SystemClock.uptimeMillis();
//        m_parentView.requestRender();
        m_fpsTimer.startCounting();

        preprocess();

        while (m_running){

            m_elapsedTime = SystemClock.uptimeMillis() - m_startTime;
            m_currentTime = SystemClock.uptimeMillis() - m_modTime;

            if (m_currentTime > m_period){

                m_modTime = SystemClock.uptimeMillis();

                handle_motionEvents();

                if (m_updateDirty == true){
                    try {
                        synchronized (m_logicLock){
                            doLogic();
                        }
                    }
                    catch (InterruptedException e){
                        Log.d(TAG, e.toString());
                    }
                    if (m_updateMode == updateMode.UPDATE_WHEN_DIRTY){
                        m_updateDirty = false;
                    }
                }

            }
            check_pause();
        }

        postprocess();
    }

    public void onPause() {
        synchronized (m_pauseLock) {
            m_paused = true;
        }
    }

    public void onResume() {
        synchronized (m_pauseLock) {
            m_paused = false;
            m_pauseLock.notifyAll();
        }
    }

    protected void check_pause(){
        synchronized (m_pauseLock) {
            while (m_paused) {
                try {
                    m_pauseLock.wait();
                } catch (InterruptedException e) {
                    Log.d(TAG, e.toString());
                }
            }
        }
    }

    public void onDestroy(){
//        Log.d(TAG, "onDestroy : Clearing table of size :" + Integer.toString(m_db.size()));
//        m_db.clear();
    }

    public void set_running(boolean _set){
        Log.d(TAG, "Changed running : " + Boolean.toString(_set));
        m_running = _set;
    }

    public void preprocess(){}
    public void postprocess(){}

    protected void handle_motionEvents(){
        if (m_motionEventQueue.get(0) != null){
            synchronized (m_motionEventLock){
                    while (m_motionEventQueue.get(0) != null){
                        m_lastMotionEvent = m_motionEventQueue.get(0);
                        try {
                            handle_motionEvent();
                        } catch (IllegalArgumentException e) {
                            Log.d(TAG, e.toString());
                        }
                        m_motionEventQueue.pop();
                    }
//                        m_lastMotionEvent.recycle();
            }
            m_lastMotionEvent = null;
        }
    }
    protected void handle_motionEvent(){}
    protected void set_motionEvent(MotionEvent _newMotionEvent){
        final MotionEvent newEvent = MotionEvent.obtain(_newMotionEvent);
        synchronized (m_motionEventLock){
            m_lastMotionEvent = newEvent;
            m_motionEventQueue.add(newEvent);
        }
    }

    public void set_period(long _period){m_period = _period;}
    public long get_period(){return m_period;}
    public long get_elapsedTime(){return m_elapsedTime;}
    public void set_updateMode(updateMode _mode){m_updateMode = _mode;}
    public updateMode get_updateMode(){return m_updateMode;}
    public void requestUpdate(){m_updateDirty = true;}

    public boolean onTouchEvent(MotionEvent _event){
        set_motionEvent(_event);
        return true;
    }

}
