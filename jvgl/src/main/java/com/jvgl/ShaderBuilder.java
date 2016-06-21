package com.jvgl;

import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by Norad on 30/06/2014.
 */
public class ShaderBuilder{
    private static final String TAG = "JVGL.ShaderBuilder";

    public static int compileShader(final int _type, final String _shaderSource){

        int handle = GLES20.glCreateShader(_type);
        if (handle != 0)
        {
            GLES20.glShaderSource(handle, _shaderSource);
            GLES20.glCompileShader(handle);

            final int[] status = new int[1];
            GLES20.glGetShaderiv(handle, GLES20.GL_COMPILE_STATUS, status, 0);

            if (status[0] == 0){
                Log.e(TAG, "ERROR: Failed to compile the shader: " + GLES20.glGetShaderInfoLog(handle));
                GLES20.glDeleteShader(handle);
                handle = 0;
            }
        }

        if (handle == 0){
            Log.e(TAG, "ERROR: Failed to create a shader");
        }
        return handle;
    }

    public static int compileProgram(final int _vertexHandle, final int _fragmentHandle, final String[] attributes){
        int handle = GLES20.glCreateProgram();

        if (handle != 0){
            GLES20.glAttachShader(handle, _vertexHandle);
            GLES20.glAttachShader(handle, _fragmentHandle);

            if (attributes != null){
                final int size = attributes.length;
                for (int i = 0; i < size; i++){
                    GLES20.glBindAttribLocation(handle, i, attributes[i]);
                }
            }

            GLES20.glLinkProgram(handle);

            final int[] status = new int[1];
            GLES20.glGetProgramiv(handle, GLES20.GL_LINK_STATUS, status, 0);

            // If the link failed, delete the program.
            if (status[0] == 0){
                Log.e(TAG, "ERROR: Failed to link the program" + GLES20.glGetProgramInfoLog(handle));
                GLES20.glDeleteProgram(handle);
                handle = 0;
            }
        }

        if (handle == 0){
            Log.e(TAG, "ERROR: Failed to create a program");
        }
        return handle;
    }
}