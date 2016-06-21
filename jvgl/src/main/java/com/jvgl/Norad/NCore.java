package com.jvgl.Norad;

import android.opengl.GLSurfaceView;


import com.jvgl.Core;


/**
 * Created by Norad on 05/08/2014.
 */
public class NCore extends Core{
    private final static String TAG = new String("JVGL.NCore ");

    protected NSceneGraph m_sceneGraph;


    public NCore(NActivity _parentActivity, GLSurfaceView _parentView){
        super(_parentActivity, _parentView);
        NBaseObject.reset_nextId();
        m_sceneGraph = new NSceneGraph();
    }

    @Override
    protected synchronized void doLogic() throws InterruptedException{
        super.doLogic();
        m_sceneGraph.update_root();
    }

    public NSceneGraph get_sceneGraph(){return m_sceneGraph;}
    public void set_sceneGraph(NSceneGraph _sceneGraph){m_sceneGraph = _sceneGraph;}
}
