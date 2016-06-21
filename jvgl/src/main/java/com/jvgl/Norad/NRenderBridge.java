package com.jvgl.Norad;

import com.jvgl.ObjectQueue;

import java.util.ArrayList;

/**
 * Created by Norad on 10/07/2014.
 */
public class NRenderBridge {
    private final static String TAG = new String("JVGL.Norad.RenderBridge ");

    private NRenderer m_renderer;
    private NSceneGraph m_sceneGraph;
    private NRenderQueue m_queue;
//    private ObjectQueue<NRenderTask, NRenderableObject> m_queue2;
    private ArrayList<NRenderTask> m_queue3;
    private byte m_bridgeState;

    private boolean m_initialised;

    public NRenderBridge(){
        m_queue = new NRenderQueue();
//        m_queue2 = new ObjectQueue<NRenderTask, NRenderableObject>(NRenderTask.class);
        m_queue3 = new ArrayList<NRenderTask>();
        m_bridgeState = 0;
        m_initialised = false;
    }

    public void initialise(NRenderer _renderer, NSceneGraph _sceneGraph){
        m_renderer = _renderer;
        m_sceneGraph = _sceneGraph;

        m_initialised = true;
    }

    public void swap_queues(){
        int currId = m_queue.get_id();
//        Log.d(TAG, "Swaping queues : "+Integer.toString(currId) +" -> "+Integer.toString(1-currId));
        m_renderer.waitTillFree();
//        m_queue = m_renderer.swap_queue(m_queue);
//        m_queue2 = m_renderer.swap_queue(m_queue2);
        m_queue3 = m_renderer.swap_queue(m_queue3);

//        if (m_bridgeState == 0){
            scan_renderableObjects();
//        }

        m_bridgeState = (byte)(1-m_bridgeState);
//        Log.d(TAG, "Swapped queues");
    }

    public void scan_renderableObjects(){
        ArrayList<NRenderableObject> awaitingObjects = m_sceneGraph.get_awaitingRenderableObjects();
        NRenderableObject object;
        NRenderTask newTask;
        NRenderTask currentTask;
        for (int i = 0 ; i < awaitingObjects.size() ; i++) {
            object = awaitingObjects.get(i);
            if (m_bridgeState == 0) {
                object.m_activeTask = object.m_renderTask0;
            } else {
                object.m_activeTask = object.m_renderTask1;
            }
            switch (object.m_objectState) {
                case 0: {
                    newTask = new NRenderTask();
                    newTask.set(object);
                    if (m_bridgeState == 0){
                        object.m_renderTask0 = newTask;
                    }
                    else{
                        object.m_renderTask1 = newTask;
                    }

                    m_queue3.add(newTask);
                    object.m_objectState = 1;
                    break;
                }
                case 1: {
                    newTask = new NRenderTask();
                    newTask.set(object);
                    if (m_bridgeState == 0){
                        object.m_renderTask0 = newTask;
                    }
                    else{
                        object.m_renderTask1 = newTask;
                    }

                    m_queue3.add(newTask);
                    object.m_objectState = 4;
                    awaitingObjects.remove(i);
                    object = null;
                    i--;
                    break;
                }
                case 2: {
                    m_queue3.remove(object.m_activeTask);
                    object.m_objectState = 3;
//                    object.swap_tasks();
                    break;
                }
                case 3: {
                    m_queue3.remove(object.m_activeTask);
                    object.m_objectState = 4;
                    awaitingObjects.remove(i);
                    object = null;
                    i--;
                    break;
                }
            }
        }
        m_sceneGraph.set_awaitingRenderableObjects(awaitingObjects);
    }

    public void update_queue() {
//        Log.d(TAG, "Updating queue : " + Integer.toString(m_queue.get_id()));
        ArrayList<NRenderableObject> baseObjects = m_sceneGraph.get_renderableObjects();
        NRenderableObject currentObject = null;
        NRenderTask currentTask;
        int size = baseObjects.size();
        for (int i = 0; i < size; i++) {
            currentObject = baseObjects.get(i);
//            if (currentObject.m_renderable) {
//                m_queue.add_task(currentObject);
//                m_queue2.put(currentObject, false);
//            }
            if (currentObject.m_objectState >= 4){
                currentTask = m_queue3.get(i);
                currentTask.update(currentObject);
            }
//            currentObject.swap_tasks();
        }
//        m_queue.cleanTasks();
//        m_queue2.cleanTasks();
    }

    public void set_renderer(NRenderer _renderer){m_renderer = _renderer;}
    public void set_sceneGraph(NSceneGraph _sceneGraph){m_sceneGraph = _sceneGraph;}
}
