package com.jvgl;

import android.util.Log;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Norad on 03/08/2014.
 */
public class AttributeQueue {
    private final static String TAG = new String("JVGL.Attribute Queue : ");

    private Map<String, ArrayList<Float>> m_queue;

    public AttributeQueue(){
        m_queue = new HashMap<String,  ArrayList<Float>>();
    }

    public void set_queue(Map<String,  ArrayList<Float>> _queue){m_queue = _queue;}
    public  Map<String,  ArrayList<Float>> get_queue (){return m_queue;}
    public int size(){return m_queue.size();}
    public void put(String _name,  ArrayList<Float> _a){
        if (m_queue.containsKey(_name)){
            Log.d(TAG, "Attribute " + _name + " exist. Couldn't put");
            return;
        }
        m_queue.put(_name, _a);
    }
    public  ArrayList<Float> get(String _name){
        if (m_queue.containsKey(_name)){
            return m_queue.get(_name);
        }
        Log.d(TAG, "Attribute " + _name + " doesn't exist. Returning null");
        return null;
    }

    public void update(String _name, ArrayList<Float> _a){
        if (m_queue.containsKey(_name)) {
            m_queue.put(_name, _a);
            return;
        }
        Log.d(TAG, "Attribute " + _name + " doesnt exist. Couldn't update");
    }
}
