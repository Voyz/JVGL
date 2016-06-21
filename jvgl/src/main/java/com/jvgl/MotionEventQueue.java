package com.jvgl;

import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by norad on 16/05/15.
 */
public class MotionEventQueue {
    protected ArrayList<MotionEvent> m_queue;
    protected int m_length = 0;




    public MotionEventQueue(){
        m_queue = new ArrayList<MotionEvent>();
        m_queue.ensureCapacity(4);
        m_queue.add(null);
        m_queue.add(null);
        m_queue.add(null);
        m_queue.add(null);
    }

    public int get_free_index(){
        m_length = m_queue.size();
        int index = m_length;
        MotionEvent currEvent;
        for (int i = m_length-1 ; i >= 0 ; i--){
            currEvent = m_queue.get(i);
            index = i;
            if (currEvent != null) {
                index = i+1;
                break;
            }
        }
        return index;
    }

    synchronized public void add(MotionEvent _event){
        int free_index = get_free_index();
        if (free_index >= m_queue.size()) {
            m_queue.add(_event);
        } else {
            m_queue.set(free_index, _event);
        }
    }

    synchronized public void pop(){
        MotionEvent toPop = m_queue.get(0);
        m_length = m_queue.size();
        for (int i = 0 ; i < m_length-1 ; i++){
            m_queue.set(i, m_queue.get(i+1));
        }
        m_queue.set(m_length-1, null);
        toPop.recycle();
    }

    synchronized public MotionEvent get(int _index){
        return m_queue.get(_index);
    }
    public MotionEvent get_next(){
        return get(0);
    }
}
