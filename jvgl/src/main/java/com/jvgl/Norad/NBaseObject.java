package com.jvgl.Norad;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Norad on 10/07/2014.
 *  TODO: Look at Replica Island's FixedSizeArray for quicker way of managing arrays
 */
public class NBaseObject {
    private final static String TAG = new String("NBaseObject ");
    private static int ms_nextId = 0;

    protected int m_id;
    protected String m_name;

    protected boolean m_updated;
    protected boolean m_rooted;
    protected short m_updateId;

    protected ArrayList<NBaseObject> m_parents;
    protected ArrayList<NBaseObject> m_children;

    protected Iterator<NBaseObject> child_it;

    public static void reset_nextId(){
        ms_nextId = 0;
    }

    public NBaseObject(){
        m_parents = new ArrayList<NBaseObject>();
        m_children = new ArrayList<NBaseObject>();
        m_id = ms_nextId++;
        m_name = "baseObject"+Integer.toString(m_id);
        m_updated = false;
        m_updateId =-1;
        m_rooted = false;
    }

    public void initialiseBaseObject(String _name){
        m_name = _name;
    }



    public void add_parent(NBaseObject _parent){
        m_parents.add(_parent);
        _parent.bounceAdd_child(this);
    }

    public void add_child(NBaseObject _child){
        m_children.add(_child);
        _child.bounceAdd_parent(this);
    }

    public void remove_child(NBaseObject _child){
        _child.bounceRemove_parent(this);
        m_children.remove(_child);
    }
    public void remove_parent(NBaseObject _parent){
        _parent.bounceRemove_child(this);
        m_parents.remove(_parent);
    }

    public void free_from_system(){
        unparent();
        unchild();
    }

    public void unparent(){
        int parentSize = m_parents.size();
        for (int i = 0 ; i < parentSize ; i++){
            m_parents.get(i).bounceRemove_child(this);
        }
        m_parents.clear();
    }

    public void unchild(){
        int childrenSize = m_children.size();
        for (int i = 0 ; i < childrenSize; i++){
            m_children.get(i).bounceRemove_parent(this);
        }
        m_children.clear();
    }

    protected void bounceAdd_parent(NBaseObject _parent){m_parents.add(_parent);}
    protected void bounceAdd_child(NBaseObject _child){m_children.add(_child);}
    protected void bounceRemove_parent(NBaseObject _parent){m_parents.remove(_parent);}
    protected void bounceRemove_child(NBaseObject _child){m_children.remove(_child);}

    public ArrayList<NBaseObject> get_parents(){return m_parents;}
    public ArrayList<NBaseObject> get_children(){return m_children;}
    public static int get_nextId(){return ms_nextId++;}

    public int get_id(){return m_id;}
    public void set_id(int _id){m_id = _id;}

    public String get_name(){return m_name;}
    public void set_name(String _name){m_name = _name;}

    public boolean get_rooted(){return m_rooted;}
    public void set_rooted(boolean _set){m_rooted = _set;}

    protected void update(short _updateId){
//        Log.d(TAG, "NBaseObject " + Integer.toString(m_id) + " / "+m_name+" updating");

        run();
        m_updateId = _updateId;
        m_updated = true;

        NBaseObject child;
        int size = m_children.size();
        for (int i = 0 ; i < size ; i++){
            child = m_children.get(i);
            if (child.m_updateId != _updateId){
                child.update(_updateId);
            }
        }

//
//        for (NBaseObject child : m_children){
//            if (child.m_updateId != _updateId){
//                child.update(_updateId);
//            }
//        }
    }

    protected void run(){}

}
