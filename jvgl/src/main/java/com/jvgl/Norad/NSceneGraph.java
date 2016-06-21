package com.jvgl.Norad;

import java.util.ArrayList;

/**
 * Created by Norad on 10/07/2014.
 */
public class NSceneGraph {

    private NBaseObject m_root;
    private short m_updateId = 0;

    private ArrayList<NBaseObject> m_baseObjects;
    private ArrayList<NRenderableObject> m_renderableObjects;
    private ArrayList<NRenderableObject> m_awaitingRenderableObjects;

    public NSceneGraph(){
        m_root = new NBaseObject();
        m_root.initialiseBaseObject("root");
        m_baseObjects = new ArrayList<NBaseObject>();
        m_renderableObjects = new ArrayList<NRenderableObject>();
        m_awaitingRenderableObjects = new ArrayList<NRenderableObject>();
    }

    public ArrayList<NBaseObject> get_baseObjects(){return m_baseObjects;}
    public ArrayList<NRenderableObject> get_renderableObjects(){return m_renderableObjects;}
    public ArrayList<NRenderableObject> get_awaitingRenderableObjects(){return m_awaitingRenderableObjects;}
    public void set_awaitingRenderableObjects(ArrayList<NRenderableObject> _list){m_awaitingRenderableObjects = _list;}
    public NBaseObject get_root(){return m_root;}
    public void set_root(NBaseObject _root){m_root = _root;}

    public void add_baseObject(NBaseObject _baseObject){
        m_baseObjects.add(_baseObject);
//        if (_baseObject.getClass().equals(NRenderableObject.class)){
//            m_renderableObjects.add((NRenderableObject)_baseObject);
//        }
    }

    public void add_baseObject(NRenderableObject _baseObject){
        m_baseObjects.add(_baseObject);
        m_renderableObjects.add(_baseObject);
        m_awaitingRenderableObjects.add(_baseObject);
    }

    public void add_baseObjectRooted(NBaseObject _baseObject){
        m_baseObjects.add(_baseObject);
        m_root.add_child(_baseObject);
        _baseObject.set_rooted(true);

    }

    public void add_baseObjectRooted(NRenderableObject _baseObject){
        m_baseObjects.add(_baseObject);
        m_renderableObjects.add(_baseObject);
        m_awaitingRenderableObjects.add(_baseObject);
        m_root.add_child(_baseObject);
        _baseObject.set_rooted(true);
    }

    public void remove_baseObject(NBaseObject _baseObject){
        m_baseObjects.remove(_baseObject);
        unroot(_baseObject);
        _baseObject.free_from_system();
    }

    public void remove_baseObject(NRenderableObject _baseObject){
//        if (_baseObject.m_objectState != 3){
            _baseObject.m_objectState = 2;
//        }
        m_awaitingRenderableObjects.add(_baseObject);

        m_baseObjects.remove(_baseObject);
        m_renderableObjects.remove(_baseObject);
//        TODO : This could be slow or broken
//        m_awaitingRenderableObjects.remove(_baseObject);
        unroot(_baseObject);
        _baseObject.free_from_system();
    }


    public void unroot(NBaseObject _baseObject){
        if (_baseObject.get_rooted()){
            m_root.remove_child(_baseObject);
            _baseObject.set_rooted(false);
        }
    }

    public void update_root(){
//        ms_updateId += ms_updateId;
//        short modulusId = (short) (ms_updateId+1);
//        m_updateId = (short) (m_updateId++  % 32766);
//        scan_awaitingRenderableObjects();
        m_root.update(new_updateId());
    }

    public short new_updateId(){
        float asd =  ((m_updateId+1)  % 32766);
        m_updateId = (short) asd;
//        m_updateId = (short) (m_updateId++  % 32766);
        return m_updateId;
    }

    public void scan_awaitingRenderableObjects(){
        NRenderableObject object;
        for (int i = 0 ; i < m_awaitingRenderableObjects.size() ; i++){
            object = m_awaitingRenderableObjects.get(i);
            if (object.m_objectState >= 4){
                m_awaitingRenderableObjects.remove(object);
                i--;
            }
        }
    }
}

