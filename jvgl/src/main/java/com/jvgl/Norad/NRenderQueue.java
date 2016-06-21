package com.jvgl.Norad;

import com.jvgl.Logger;
import com.jvgl.Mesh;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Norad on 10/07/2014.
 * // TODO: could report if it is dirty
 */
public class NRenderQueue {
    private final static String TAG = new String("JVGL.Norad.RenderQueue ");
    private final static int VBCT = 0;

    private static int ms_nextId = 0;
    private int m_id;
    private ArrayList<NRenderTask> m_renderTasks;
    private NRenderTask[] m_array;
    private byte[] m_arrayActive;
    private Iterator<NRenderTask> m_rt_it;

    public NRenderQueue(){
        m_id = ms_nextId++;
        m_renderTasks = new ArrayList<NRenderTask>();
        m_array = new NRenderTask[]{};
    }

    public void set_renderTasks(ArrayList<NRenderTask> _renderTasks){
        m_renderTasks = _renderTasks;}
    public ArrayList<NRenderTask> get_renderTasks(){return m_renderTasks;}
//    public int size(){return m_renderTasks.size();}
//    public NRenderTask get(int _index){return m_renderTasks.get(_index);}
    public int size(){return m_array.length;}
    public NRenderTask get(int _index){return m_array[_index];}
    public int get_id(){return m_id;}

    private void push_back(NRenderTask _rt){
        int oldSize = m_array.length;
        NRenderTask[] new_array = new NRenderTask[oldSize+1];
        byte[] new_arrayActive = new byte[oldSize+1];
        for (int i = 0 ; i < oldSize ; i++){
            new_array[i] = m_array[i];
            new_arrayActive[i] = m_arrayActive[i];
        }
        new_array[oldSize] = _rt;
        new_arrayActive[oldSize] = 1;
        m_array = new_array;
        m_arrayActive = new_arrayActive;
    }

    public NRenderTask add_task(NRenderableObject _mesh){
        int id = _mesh.m_id;
        NRenderTask task = findById(id, m_renderTasks);
        NRenderTask newTask;

        if (task == null){
            newTask = new NRenderTask();
            newTask.set(_mesh);

            push_back(newTask);
            m_renderTasks.add(newTask);
        }
        else{
            task.update(_mesh);
            newTask = task;
        }
        return newTask;
    }

    public NRenderTask findById(int _id, ArrayList<NRenderTask> _layer){
        int currentId;
        NRenderTask rt = null;
        int size = m_array.length;
//        m_rt_it = m_renderTasks.iterator();
//        while(m_rt_it.hasNext()){
//            currentId = m_rt_it.next().m_id;
//            if(_id == currentId){
//                return rt;
//            }
//        }
        for (int i = 0 ; i < size ; i++){

//            rt = _layer.get(i);
            rt = m_array[i];
            currentId = rt.m_id;
            if(_id == currentId){
                return rt;
            }
        }
        return null;
    }

    public NRenderTask findByName(String _name, ArrayList<NRenderTask> _layer){
        String currentName;
        NRenderTask rt = null;
        int size = _layer.size();
        for (int i = 0 ; i < size ; i++){
            rt = _layer.get(i);
            currentName = rt.m_name;
            if(_name.equals(currentName)){
                return rt;
            }
        }
        return null;
    }

    public void cleanTasks(){
//        ArrayList<NRenderTask> tasksToDelete = new ArrayList<NRenderTask>();
        NRenderTask rt = null;
//        int renderTasks_size = m_renderTasks.size();
        int renderTasks_size = m_array.length;
        boolean dirty = false;
        for (int i = 0 ; i < renderTasks_size ; i++){
            rt = m_array[i];
//            rt = m_renderTasks.get(i);
            if (!rt.m_active){
//                tasksToDelete.add(rt);
                m_arrayActive[i] = 0;
                dirty = true;
            }
        }
        if (dirty){
            collectGarbage();
        }

//        int tasksToDelete_size = tasksToDelete.size();
//        for (int i = 0 ; i < tasksToDelete_size ; i++){
//            rt = tasksToDelete.get(i);
//            m_renderTasks.remove(rt);
//        }
    }

    public void collectGarbage(){
//        Logger.d(1, VBCT, TAG, "Collecting Garbage");
        int size = m_array.length;
        int newSize = 0;
        for (int i = 0 ; i < size ; i++){
            newSize += (int) m_arrayActive[i];
        }
        NRenderTask[] new_array = new NRenderTask[newSize];
        byte[] new_arrayActive = new byte[newSize];
        int it = 0;
        for (int i = 0 ; i < size ; i++){
            if (m_arrayActive[i] == 1){
                new_array[it] = m_array[i];
                new_arrayActive[it] = m_arrayActive[i];
                it++;
            }
        }
        m_array = new_array;
        m_arrayActive = new_arrayActive;
    }

}
