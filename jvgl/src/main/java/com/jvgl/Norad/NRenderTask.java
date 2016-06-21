package com.jvgl.Norad;

import com.jvgl.AttributeQueue;
import com.jvgl.Mesh;
import com.jvgl.QueueObject;
import com.jvgl.ShaderObject;
import com.jvgl.VBO;
import com.jvgl.Matrix4f;

/**
 * Created by Norad on 05/07/2014.
 */
public class NRenderTask implements QueueObject {
    public VBO m_vbo;
    public ShaderObject m_shader;
    public String m_name;
    public int m_id;
    public Matrix4f m_transMat;
    public boolean m_active;
    public boolean m_constant;
    public byte m_taskState;
    public NRenderableObject m_renderableObject;

    //    public Vec3 m_colourModifier;
//    private Map<String, Float> m_renderAttributes;
    private AttributeQueue m_renderAttributes;

    public NRenderTask(){
        m_vbo = null;
        m_taskState = 0;
    }

    public void set(NRenderableObject _mesh){
        m_renderableObject = _mesh;
        m_vbo = _mesh.m_vbo;
        m_name = _mesh.m_name;
        m_id = _mesh.m_id;
        update(_mesh);
    }


    public void update(NRenderableObject _mesh){
        int mode = _mesh.m_mode;

        if (_mesh.m_renderable == true){
            m_taskState = 3;
        }
        else{
            m_taskState = 2;
        }
        if ((m_vbo != null)){
            if (_mesh.m_modified){
//                if (mode == GLES20.GL_TRIANGLES){
////                    m_converter->convertMeshFaces(_mesh->get_meshConst(), m_vao);
//                    _mesh.m_modified = false;
//                }
//                else if (mode == GLES20.GL_LINES){
////                    m_converter->convertMeshLines(_mesh->get_meshConst(), m_vao);
//                    _mesh.m_modified = false;
//                }
                m_vbo = _mesh.m_vbo;
                _mesh.m_modified = false;
            }
        }
        else{
            System.out.println("WARNING : VAO unallocated, can not convert. Id :" + m_name);
        }
        m_transMat = _mesh.m_transMat;
        m_shader = _mesh.m_shader;
//        m_colourModifier = _mesh.m_colourModifier;
        m_renderAttributes = _mesh.get_renderAttributes();
        m_active = true;
    }

    public void render(){
        m_shader.set_renderAttributes(m_renderAttributes);
        m_shader.setupLocal();
        m_shader.loadToShader();
        m_vbo.render(0);
        m_active = false;
    }


    public void set(Mesh _mesh){
        m_name = _mesh.m_id;
        m_vbo = _mesh.m_vbo;
        update(_mesh);
    }

    public void update(Mesh _mesh){
        int mode = _mesh.m_mode;

        if ((m_vbo != null)){
            if (_mesh.m_modified){
//                if (mode == GLES20.GL_TRIANGLES){
////                    m_converter->convertMeshFaces(_mesh->get_meshConst(), m_vao);
//                    _mesh.m_modified = false;
//                }
//                else if (mode == GLES20.GL_LINES){
////                    m_converter->convertMeshLines(_mesh->get_meshConst(), m_vao);
//                    _mesh.m_modified = false;
//                }
                m_vbo = _mesh.m_vbo;
                _mesh.m_modified = false;
            }
        }
        else{
            System.out.println("WARNING : VAO unallocated, can not convert. Id :" + m_name);
        }
        m_transMat = _mesh.m_transMat;
//        m_shader = _mesh.m_shader;
        m_active = true;
    }

    public void set_queueObject(QueueObject _dependant){
        NRenderableObject _mesh = (NRenderableObject) _dependant;
        m_name = _mesh.m_name;
        m_id = _mesh.m_id;
        m_vbo = _mesh.m_vbo;
        update(_mesh);
    }

    //    @Override
    public void update_queueObject(QueueObject _dependant){
        NRenderableObject _mesh = (NRenderableObject) _dependant;
        int mode = _mesh.m_mode;

        if ((m_vbo != null)){
            if (_mesh.m_modified){
//                if (mode == GLES20.GL_TRIANGLES){
////                    m_converter->convertMeshFaces(_mesh->get_meshConst(), m_vao);
//                    _mesh.m_modified = false;
//                }
//                else if (mode == GLES20.GL_LINES){
////                    m_converter->convertMeshLines(_mesh->get_meshConst(), m_vao);
//                    _mesh.m_modified = false;
//                }
                m_vbo = _mesh.m_vbo;
                _mesh.m_modified = false;
            }
        }
        else{
            System.out.println("WARNING : VAO unallocated, can not convert. Id :" + m_name);
        }
        m_transMat = _mesh.m_transMat;
        m_shader = _mesh.m_shader;
//        m_colourModifier = _mesh.m_colourModifier;
        m_renderAttributes = _mesh.get_renderAttributes();
    }

    public int get_id(){return m_id;}
    public boolean get_active(){return m_active;}
    public boolean get_constant(){return m_constant;}
    public void set_id(int _id){m_id = _id;}
    public void set_active(boolean _active){m_active = _active;}
    public void set_constant(boolean _constant){m_constant = _constant;}
}
