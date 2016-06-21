package com.jvgl;

import android.graphics.Shader;
import android.util.ArrayMap;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Norad on 13/07/2014.
 */
public class ShaderLibrary {
    private static ShaderLibrary instance = null;
    private final static String TAG = new String("JVGL.ShaderLibrary : ");


    private Map<String, ShaderObject> m_shaders;
    private ShaderObject m_currentShaderObject;

    protected ShaderLibrary(){
        m_shaders = new HashMap<String, ShaderObject>();
    };

    public static ShaderLibrary getInstance(){
        if (instance == null){
            instance = new ShaderLibrary();
        }
        return instance;
    }

    public void set_currentShaderObject(String _name){
        if (m_shaders.containsKey(_name)){
            m_currentShaderObject = m_shaders.get(_name);
        }
        else{
            Log.d(TAG, "Shader object " + _name + " doesn't exist");
        }

    }
    public ShaderObject create_shaderObject(String _name){
        ShaderObject newShaderObject = new ShaderObject(_name);
        m_shaders.put(_name, newShaderObject);
        m_currentShaderObject = newShaderObject;
        return newShaderObject;
    }

    public void add_shaderObject(String _name, ShaderObject _newShader){
        m_shaders.put(_name, _newShader);
        m_currentShaderObject = _newShader;
    }

    public void set_shaders(int _vertexShaderSource, int _fragmentShaderSource){
        m_currentShaderObject.set_shaders(_vertexShaderSource, _fragmentShaderSource);
    }

    public void compile_program(String[] _attributes){
        m_currentShaderObject.compile_program(_attributes);
    }

    public void use_shaderObject(){
        m_currentShaderObject.use();
    }

    public void use_shaderObject(String _name){
        if(m_shaders.containsKey(_name)){
            m_shaders.get(_name).use();
        }
    }

    public ShaderObject get_shaderObject(){
        return m_currentShaderObject;
    }

    public ShaderObject get_shaderObject(String _name){
        if (m_shaders.containsKey(_name)){
            return m_shaders.get(_name);
        }
        Log.d(TAG, "Shader object " + _name + " doesn't exist. Returning null");
        return null;
    }
}
