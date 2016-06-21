package com.jvgl.Norad;

import android.opengl.GLES20;

import com.jvgl.AttributeQueue;
import com.jvgl.QueueObject;
import com.jvgl.ShaderObject;
import com.jvgl.VBO;
import com.jvgl.Matrix4f;

import java.util.ArrayList;

/**
 * Created by Norad on 10/07/2014.
 */
public class NRenderableObject extends NBaseObject{
    public VBO m_vbo;
//    public String m_shader;
    public ShaderObject m_shader;
    public Matrix4f m_transMat;
    public int m_mode;

    public boolean m_modified;
    public boolean m_renderable;
    public byte m_objectState;
    //    public Vec3 m_colourModifier;

//    private Map<String, Float> m_renderAttributes;
    protected AttributeQueue m_renderAttributes;

    public boolean m_constant;
    public boolean m_active;
    public NRenderTask m_renderTask0;
    public NRenderTask m_renderTask1;
    public NRenderTask m_activeTask;
    public NRenderTask m_passiveTask;

    public NRenderableObject(){

//        m_shader = "";
//        m_id = "1";
//        m_renderAttributes = new HashMap<String, Float>();
        m_renderAttributes = new AttributeQueue();
        m_transMat = new Matrix4f();
        m_mode = GLES20.GL_TRIANGLES;
        m_modified = true;
        m_renderable = true;
        m_objectState = 0;
    }

    public void initialise(){

    }

    public void swap_tasks(){
        NRenderTask currentTask = m_activeTask;
        m_activeTask = m_passiveTask;
        m_passiveTask = currentTask;
    }

    public void set_vbo(VBO _vbo){m_vbo = _vbo;}
    public void set_shader(ShaderObject _shader){m_shader = _shader;}
    public void set_renderable(boolean _set){m_renderable = _set;}
//    public void set_colourModifier(Vec3 _rgb){m_colourModifier.set(_rgb);}
//    public void set_colourModifier(float _r, float _g, float _b){m_colourModifier.set(_r, _g, _b);}

    public void add_renderAttribute(String _name, ArrayList<Float> _attribute){m_renderAttributes.put(_name, _attribute);}
    public void update_renderAttribute(String _name, ArrayList<Float> _attribute){m_renderAttributes.update(_name, _attribute);}
//    public Map<String, Float> get_renderAttributes(){return m_renderAttributes;}
    public AttributeQueue get_renderAttributes(){
        if (m_renderAttributes.size() == 0){
            return null;
        }
        else {
            return m_renderAttributes;
        }
    }

//    //    @Override
//    public void set_queueObject(QueueObject _dependant){
//    }
//
//    //    @Override
//    public void update_queueObject(QueueObject _dependant){
//    }

//    public int get_id(){return m_id;}
//    public boolean get_active(){return m_active;}
//    public boolean get_constant(){return m_constant;}
//    public void set_id(int _id){m_id = _id;}
//    public void set_active(boolean _active){m_active = _active;}
//    public void set_constant(boolean _constant){m_constant = _constant;}
}
