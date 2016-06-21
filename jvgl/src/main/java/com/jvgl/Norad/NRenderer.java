package com.jvgl.Norad;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.jvgl.Matrix4f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


/**
 * Created by Norad on 28/06/2014.
 */
public class NRenderer implements GLSurfaceView.Renderer{
    private final static String TAG = new String("JVGL.Renderer ");
    private final static int VBCT = 2;


    private final NActivity m_parentActivity;
    private final GLSurfaceView m_glSurfaceView;

    private NRenderQueue m_queue;
//    private ObjectQueue<NRenderTask, NRenderableObject> m_queue2;
    private ArrayList<NRenderTask> m_queue3;
//    private NRenderQueue m_passiveQueue;
    public NRenderTask m_bgTask = null;

    private Matrix4f m_globalTransform;
    private Matrix4f m_currentTransform;
    private Matrix4f m_projection;
    private Matrix4f m_view;

    private boolean m_doScreenshot = false;
    private Bitmap m_lastScreenshot = null;

    public int m_width;
    public int m_height;

    private Object m_screenshotLock;



    public NRenderer(final NActivity _appActivity, final GLSurfaceView glSurfaceView, final Matrix4f _globalTransform) {

        m_globalTransform = _globalTransform;
        m_parentActivity = _appActivity;
        m_glSurfaceView = glSurfaceView;

        m_screenshotLock = new Object();

        m_queue = new NRenderQueue();
//        m_queue2 = new ObjectQueue<NRenderTask, NRenderableObject>(NRenderTask.class);
        m_queue3 = new ArrayList<NRenderTask>();

//        m_passiveQueue = new NRenderQueue();
        m_projection = new Matrix4f();
        m_view = new Matrix4f();
        m_currentTransform = new Matrix4f();


        String version = GLES20.glGetString(GLES20.GL_SHADING_LANGUAGE_VERSION);
        Log.d(TAG, "GLSL Version : " + version);
    }

    public synchronized void set_queue(NRenderQueue _queue){
        m_queue = _queue;}

    public synchronized void set_doScreenshot(boolean _set){m_doScreenshot = _set;}
    public Bitmap get_lastScreenshot(){
        while (m_lastScreenshot == null) {}
        synchronized (m_screenshotLock) {
            Bitmap toReturn = m_lastScreenshot;
            m_lastScreenshot = null;
            return toReturn;
        }
    }
    public synchronized NRenderQueue swap_queue(NRenderQueue _queue){
        NRenderQueue currentQueue = m_queue;
        m_queue = _queue;
        return currentQueue;
    }

//    public synchronized ObjectQueue swap_queue(ObjectQueue _queue){
//        ObjectQueue currentQueue = m_queue2;
//        m_queue2 = _queue;
//        return currentQueue;
//    }

    public synchronized ArrayList<NRenderTask> swap_queue(ArrayList<NRenderTask> _queue){
        ArrayList<NRenderTask> currentQueue = m_queue3;
        m_queue3 = _queue;
        return currentQueue;
    }
//    private synchronized void swap_activeQueue(){
//        NRenderQueue currentActiveQueue = m_queue;
//        m_queue = m_passiveQueue;
//        m_passiveQueue = currentActiveQueue;
//    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config){
        GLES20.glClearColor(0.3f, 0.3f, 0.3f, 0.3f);
//        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        final float eyeX = 0.0f;    final float eyeY = 0.0f;    final float eyeZ = 0;
        final float lookX = 0.0f;   final float lookY = 0.0f;   final float lookZ = -5.0f;
        final float upX = 0.0f;     final float upY = 1.0f;     final float upZ = 0.0f;

        android.opengl.Matrix.setLookAtM(m_view.getArray(), 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);


        m_width = m_glSurfaceView.getWidth();
        m_height = m_glSurfaceView.getHeight();

        m_parentActivity.onCreatedRenderer();

    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height){
        GLES20.glViewport(0,0,width, height);

        final float ratio = (float) width/height;
        float left;
        float right;
        float bottom;
        float top;
        if (width < height){
            left = -ratio;
            right = ratio;
            bottom = -1.0f;
            top = 1.0f;
        } else {
            left = -1.0f;
            right = 1.0f;
            bottom = -1/ratio;
            top = 1/ratio;
        }
        final float near = 1.0f;
        final float far = 10000.0f;

        android.opengl.Matrix.frustumM(m_projection.getArray(), 0, left, right, bottom, top, near, far);
    }

    @Override
    public void onDrawFrame(GL10 glUnused){
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
//        swap_activeQueue();
        render();
        if (m_doScreenshot){
            take_screenshot();
        }
    }

    private synchronized void render(){

        if (m_bgTask != null){
            m_bgTask.m_shader.use();
            m_bgTask.render();
            GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT);
        }
        NRenderTask currentTask = null;
//        int size = m_queue.size();
//        int size = m_queue2.size();
        int size = m_queue3.size();

//        Logger.d(2, VBCT, TAG, "Rendering : " + Integer.toString(m_queue.get_id()) + " : size : " + Integer.toString(size));

        for (int i = 0 ; i < size ; i++){

//            currentTask = m_queue.get(i);
//            currentTask = m_queue2.get(i);
            currentTask = m_queue3.get(i);
            if (currentTask.m_taskState >= 3){
                currentTask.m_shader.use();

                m_currentTransform.load(currentTask.m_transMat);
                currentTask.m_shader.set_mvp(m_globalTransform, m_currentTransform, m_view, m_projection);

                currentTask.render();
            }
        }
    }

    private synchronized void take_screenshot(){
        synchronized (m_screenshotLock) {
            int screenshotSize = m_width * m_height;
            ByteBuffer bb = ByteBuffer.allocateDirect(screenshotSize * 4);
            bb.order(ByteOrder.nativeOrder());
            GLES20.glReadPixels(0, 0, m_width, m_height, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, bb);
            int pixelsBuffer[] = new int[screenshotSize];
            bb.asIntBuffer().get(pixelsBuffer);
            bb = null;

            for (int i = 0; i < screenshotSize; ++i) {
                // The alpha and green channels' positions are preserved while the red and blue are swapped
                pixelsBuffer[i] = ((pixelsBuffer[i] & 0xff00ff00)) | ((pixelsBuffer[i] & 0x000000ff) << 16) | ((pixelsBuffer[i] & 0x00ff0000) >> 16);
            }

            m_lastScreenshot = Bitmap.createBitmap(m_width, m_height, Bitmap.Config.ARGB_8888);
            m_lastScreenshot.setPixels(pixelsBuffer, screenshotSize - m_width, -m_width, 0, 0, m_width, m_height);
            m_doScreenshot = false;
        }
    }

    public synchronized void waitTillFree(){}

}
