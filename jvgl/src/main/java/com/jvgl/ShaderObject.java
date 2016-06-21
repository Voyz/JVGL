package com.jvgl;

import android.nfc.Tag;
import android.opengl.GLES20;
import android.util.Log;

import com.jvgl.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Norad on 12/07/2014.
 */
public class ShaderObject {
    protected final static String TAG = "JVGL.ShaderObject";
    protected String m_shaderName;
    protected int m_id;
    protected String m_vertexShader;
    protected String m_fragmentShader;
    protected int m_vertexShaderHandle;
    protected int m_fragmentShaderHandle;
    protected int m_programHandle;

    protected Map<String, Integer> m_handles;
    protected Matrix4f m_mvp;

    protected Matrix4f m_view;
    protected Matrix4f m_projection;
    protected Matrix4f m_model;
    protected Matrix4f m_localTransform;
    protected Matrix4f m_globalTransform;
    protected int m_mvpHandle;

    protected boolean m_compiled;
    protected boolean m_shaded;
    protected boolean m_dirty;

    protected AttributeQueue m_renderAttributes = null;

    public ShaderObject(){
        m_shaderName = "-1";
        initialise();
    }

    public ShaderObject(String _name){
        m_shaderName = _name;
        initialise();
    }

    public void initialise(){
        m_handles = new HashMap<String, Integer>();
        m_compiled = false;
        m_shaded = false;
        m_dirty = false;
        m_mvp = new Matrix4f();
        m_view= new Matrix4f();
        m_projection = new Matrix4f();
        m_model = new Matrix4f();
        m_globalTransform = new Matrix4f();
        m_localTransform = new Matrix4f();
    }

    public void loadToShader(){
//        m_mvp.inverse();
        if (m_dirty){
            Log.w(TAG, m_shaderName+" : WARNING : Matrices not yet loaded");
        }
        GLES20.glUniformMatrix4fv(m_mvpHandle, 1, false, m_mvp.getArray(), 0);
    }


    public void calculate_mvp() {
        m_mvp.loadIdentity();
        m_mvp.mmultiply(m_model);
        m_mvp.mmultiply(m_view);
        m_mvp.mmultiply(m_projection);
//        m_mvp.multiply(m_projection);
//        m_mvp.multiply(m_view);
//        m_mvp.multiply(m_model);
        m_dirty = false;
    }

    public void calculate_model(){
        m_model.loadIdentity();
        m_model.mmultiply(m_localTransform);
        m_model.mmultiply(m_globalTransform);
//        m_model.multiply(m_globalTransform);
//        m_model.multiply(m_localTransform);
    }

    public void set_mvp(Matrix4f _matrix){m_mvp = _matrix;}
    public void set_view(Matrix4f _matrix){m_view = _matrix;}
    public void set_projection(Matrix4f _matrix){m_projection = _matrix;}
    public void set_model(Matrix4f _matrix){m_model = _matrix;}
    public void set_globalTransform(Matrix4f _matrix){m_globalTransform = _matrix;}
    public void set_localTransform(Matrix4f _matrix){m_localTransform = _matrix;}

    public void set_mvp(Matrix4f _model, Matrix4f _view, Matrix4f _projection){
        m_model = _model;
        m_view = _view;
        m_projection = _projection;
        calculate_mvp();
    }
    public void set_mvp(Matrix4f _view, Matrix4f _projection){
        m_view = _view;
        m_projection = _projection;
        calculate_mvp();
    }

    public void set_mvp(Matrix4f _globalTransform, Matrix4f _localTransform, Matrix4f _view, Matrix4f _projection){
        m_globalTransform = _globalTransform;
        m_localTransform = _localTransform;
        calculate_model();
        m_view = _view;
        m_projection = _projection;
        calculate_mvp();
    }

    public void set_model(Matrix4f _globalTransform, Matrix4f _localTransform){
        m_globalTransform = _globalTransform;
        m_localTransform = _localTransform;
        calculate_model();
    }

    public void set_mvpHandle(int _handle){m_mvpHandle = _handle;}

    public void set_shaders(String _vertexShader, String _fragmentShader){
        m_vertexShader =  _vertexShader;
        m_fragmentShader = _fragmentShader;

        m_vertexShaderHandle = ShaderBuilder.compileShader(GLES20.GL_VERTEX_SHADER, m_vertexShader);
        m_fragmentShaderHandle = ShaderBuilder.compileShader(GLES20.GL_FRAGMENT_SHADER, m_fragmentShader);
        m_shaded = true;
    }

    public void set_shaders(int _vertexShaderSource, int _fragmentShaderSource){
        Log.d(TAG,  m_shaderName+" : Make sure to modify manifest with : android:name=\"com.jvgl.App\"");
        m_vertexShader = ResourceReader.readFromResource(App.getContext(), _vertexShaderSource);
        m_fragmentShader = ResourceReader.readFromResource(App.getContext(), _fragmentShaderSource);

        m_vertexShaderHandle = ShaderBuilder.compileShader(GLES20.GL_VERTEX_SHADER, m_vertexShader);
        m_fragmentShaderHandle = ShaderBuilder.compileShader(GLES20.GL_FRAGMENT_SHADER, m_fragmentShader);
        m_shaded = true;
    }

    public void compile_program(String[] _attributes){
        if (!m_shaded){
            Log.w(TAG,  m_shaderName+" : WARNING : Program not yet shaded. Probably compiling wrong shaders");
        }
        m_programHandle = ShaderBuilder.compileProgram(m_vertexShaderHandle, m_fragmentShaderHandle,
                _attributes);

        for (int i = 0 ; i < _attributes.length ; i++){
            GLES20.glBindAttribLocation(m_programHandle, i, _attributes[i]);
            m_handles.put(_attributes[i], i);
            Log.d(TAG, "Attribute index : "+Integer.toString(i) + " | name : " + _attributes[i]);
        }
        m_compiled = true;
    }

    public void use(){
        m_dirty = true;
        GLES20.glUseProgram(m_programHandle);
    }

    public void reset_handles(){
        m_handles.clear();
    }

    public int set_uniformHandle(String _name){
        if (!m_compiled){
            Log.w(TAG,  m_shaderName+" : WARNING : Program not yet compiled. Probably setting wrong handles");
        }
        if (m_handles.containsKey(_name)){
            return m_handles.get(_name);
        }

        int handle = GLES20.glGetUniformLocation(m_programHandle, _name);
        m_handles.put(_name, handle);
        return handle;
    }

    public int set_attributeHandle(String _name){
        if (!m_compiled){
            Log.w(TAG,  m_shaderName+" : WARNING : Program not yet compiled. Probably setting wrong handles");
        }
        if (m_handles.containsKey(_name)){
            return m_handles.get(_name);
        }

        int handle = GLES20.glGetAttribLocation(m_programHandle, _name);
        m_handles.put(_name, handle);
        return handle;
    }

    public int get_handle(String _name){
        if (m_handles.containsKey(_name)){
            return m_handles.get(_name);
        }
        Log.d(TAG,  m_shaderName+" : Handle " + _name + " doesn't exist. Returning zero");
        return 0;
    }

    public void set_name(String _name){m_shaderName = _name;}

    public void set_renderAttributes(AttributeQueue _renderAttributes){m_renderAttributes = _renderAttributes;}

    public void setupLocal(){}

    public int get_programHandle(){return m_programHandle;}
}
