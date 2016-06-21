package com.jvgl;

import android.opengl.GLES20;
import android.opengl.Matrix;
import com.jvgl.Matrix4f;
/**
 * Created by Norad on 05/07/2014.
 */
public class Mesh {
    public VBO m_vbo;
    // TODO : Change to ShaderObject
    public String m_shader;
    public String m_id;
    public Matrix4f m_transMat;
    public boolean m_modified;
    public int m_mode;

    public Mesh(){
        m_shader = "";
        m_id = "1";
        m_transMat = new Matrix4f();
        m_modified = true;
        m_mode = GLES20.GL_TRIANGLES;
    }

    public void initialise(){

    }

    public void setVbo(VBO _vbo){m_vbo = _vbo;}
}
