package com.jvgl.Norad;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.SystemClock;
import android.util.Log;
import android.view.Display;

import com.jvgl.FPSTimer;
import com.jvgl.Logger;
import com.jvgl.ShaderLibrary;


/**
 * Created by Norad on 05/08/2014.
 */
public class NGLCore extends NCore {
    private final static String TAG = new String("JVGL.NGLCore ");

    protected NRenderer m_renderer;
    protected NRenderBridge m_renderBridge;

    public int m_width;
    public int m_height;


    public NGLCore(NActivity _parentActivity, GLSurfaceView _parentView, NRenderer _renderer){

        super(_parentActivity, _parentView);

        m_renderer = _renderer;

        m_renderBridge = new NRenderBridge();
        m_renderBridge.set_renderer(m_renderer);
        m_renderBridge.set_sceneGraph(m_sceneGraph);

        /// !!! DON'T SET UP ANY OPENGL HERE !!!

    }

    @Override
    public synchronized void setup(){
        super.setup();
        int screenMaxX = m_parentView.getWidth();
        int screenMaxY = m_parentView.getHeight();
        m_width = screenMaxX;
        m_height = screenMaxY;
        Logger.d(0,VBCT, TAG, "Width : "+Integer.toString(m_width)+" | Height : "+Integer.toString(m_height));
    }

    @Override
    protected synchronized void doLogic() throws InterruptedException {
        super.doLogic();
        m_renderBridge.swap_queues();
        m_renderBridge.update_queue();
//        m_parentView.requestRender();
        m_fpsTimer.stopAndPost();

//        m_renderer.addRenderTask(m_mesh);
    }

    @Override
    public void preprocess(){
        super.preprocess();
        if (m_parentView.getRenderMode() == GLSurfaceView.RENDERMODE_WHEN_DIRTY){
            m_renderBridge.swap_queues();
            m_renderBridge.update_queue();
            m_parentView.requestRender();
        }
    }
}
