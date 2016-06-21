package com.jvgl;

import android.opengl.GLES20;
import android.util.Log;

import java.util.Arrays;

/**
 * Created by Norad on 05/08/2014.
 */
public class VBOShelf {
    private final static String TAG = new String("JVGL.VBOShelf ");

    public static float[] get_boidArray(float _size){
        final float[] boidVertices = {
                // Triangle1
                0, 2*_size, 0,
                1, 0.65f, 0.2f, 0,
                _size, 0.8f*_size, 0,
                0.85f, 0.5f, 0.15f, 0,
                -_size, 0.8f * _size, 0,
                0.85f, 0.5f, 0.15f, 0,

                // Triangle2
                0, -0.6f * _size, 0,
                0.8f, 0.5f, 0.15f, 0,

                // Triangle3
                -0.8f*_size, -2 * _size, 0,
                0.7f, 0.4f, 0.15f, 0,

                0.8f*_size, -2 * _size, 0,
                0.7f, 0.4f, 0.15f, 0,
        };
        return boidVertices;
    }
    
    public static short[] get_boidIndexArray(){

        final short[] boidIndices = {
                // Triangle1
                0,1,2,
                // Triangle2
                2,1,3,
                // Triangle3
                3,5,4
        };
        return boidIndices;
    }

    public static VBO get_boidVBO(float _size){
        VBO vbo = new VBO(GLES20.GL_TRIANGLES);
        PrimitiveArray boidIndices = new PrimitiveArray(get_boidIndexArray());
        vbo.setRawIndexedData(get_boidArray(_size), 9, boidIndices, GLES20.GL_UNSIGNED_SHORT, GLES20.GL_STATIC_DRAW);
        vbo.setIndicesCount(9);
        vbo.setHandles(new int[]{0, 1});
        vbo.setStride(new int[]{3, 4});
        return vbo;
    }

    public static float[] get_cubePurpleArray(float _width, float _height, float _depth){
        float a = 0.5f;
        float b = 0.2f;
        float c = 0.8f;
        float[] cubeVertices = new float[] {
                -_width, -_height, _depth,      a,b,c,0,
                _width, -_height, _depth,       a,b,b,0,
                _width, _height, _depth,        a,b,b,0,
                -_width, _height, _depth,       a,b,a,0,
                -_width, -_height, -_depth,     c,b,a,0,
                _width, -_height, -_depth,      c,a,a,0,
                _width, _height, -_depth,       c,b,b,0,
                -_width, _height, -_depth,      c,a,b,0
        };
        return cubeVertices;
    }

    public static float[] get_cubeRedNormalsArray(float _width, float _height, float _depth){
        float r = 0.8f,  g = 0.2f, b = 0.3f;
        float[] cubeVertices = new float[] {
                -_width, -_height, _depth,      r,g,b,0,    0,0,0,
                _width, -_height, _depth,       r,g,b,0,    0,0,0,
                _width, _height, _depth,        r,g,b,0,    0,0,0,
                -_width, _height, _depth,       r,g,b,0,    0,0,0,
                -_width, -_height, -_depth,     r,g,b,0,    0,0,0,
                _width, -_height, -_depth,      r,g,b,0,    0,0,0,
                _width, _height, -_depth,       r,g,b,0,    0,0,0,
                -_width, _height, -_depth,      r,g,b,0,    0,0,0
        };
        return cubeVertices;
    }

    public static float[] get_cubeGreyNormalsArray(float _width, float _height, float _depth){
        float a = 0.5f,  b = 0.5f, c = 0.5f;
        float[] cubeVertices = new float[] {
                -_width, -_height, _depth,      c,a,c,0,    0,0,0,
                _width, -_height, _depth,       a,b,b,0,    0,0,0,
                _width, _height, _depth,        a,b,b,0,    0,0,0,
                -_width, _height, _depth,       a,b,a,0,    0,0,0,
                -_width, -_height, -_depth,     c,b,a,0,    0,0,0,
                _width, -_height, -_depth,      a,b,c,0,    0,0,0,
                _width, _height, -_depth,       c,b,b,0,    0,0,0,
                -_width, _height, -_depth,      c,a,b,0,    0,0,0
        };
        return cubeVertices;
    }

    public static float[] get_cubeGreenArray(float _width, float _height, float _depth){
        float a = 0.5f,  b = 0.8f, c = 0.2f;
        float[] cubeVertices = new float[] {
                -_width, -_height, _depth,      c,a,c,0,
                _width, -_height, _depth,       a,b,b,0,
                _width, _height, _depth,        a,b,b,0,
                -_width, _height, _depth,       a,b,a,0,
                -_width, -_height, -_depth,     c,b,a,0,
                _width, -_height, -_depth,      a,b,c,0,
                _width, _height, -_depth,       c,b,b,0,
                -_width, _height, -_depth,      c,a,b,0
        };
        return cubeVertices;
    }

    public static float[] get_cubeArray(float _width, float _height, float _depth){
        float[] cubeVertices = new float[] {
                -_width, -_height, _depth,
                _width, -_height, _depth,
                _width, _height, _depth,
                -_width, _height, _depth,
                -_width, -_height, -_depth,
                _width, -_height, -_depth,
                _width, _height, -_depth,
                -_width, _height, -_depth
        };
        return cubeVertices;
    }

    public static float[] get_cubeGreenNormalsArray(float _width, float _height, float _depth){
        float a = 0.5f;
        float b = 0.8f;
        float c = 0.2f;
        float[] cubeVertices = new float[] {
                -_width, -_height, _depth,      c,a,c,0,    -_width, -_height, _depth,
                _width, -_height, _depth,       a,b,b,0,     _width, -_height, _depth,
                _width, _height, _depth,        a,b,b,0,     _width, _height, _depth,
                -_width, _height, _depth,       a,b,a,0,    -_width, _height, _depth,
                -_width, -_height, -_depth,     c,b,a,0,    -_width, -_height, -_depth,
                _width, -_height, -_depth,      a,b,c,0,    _width, -_height, -_depth,
                _width, _height, -_depth,       c,b,b,0,    _width, _height, -_depth,
                -_width, _height, -_depth,      c,a,b,0,    -_width, _height, -_depth
        };
        return cubeVertices;
    }

    public static short[] get_cubeIndexArray(){
        short[] cubeIndices = new short[] {
                0, 1, 2, 2, 3, 0,
                3, 2, 6, 6, 7, 3,
                7, 6, 5, 5, 4, 7,
                4, 0, 3, 3, 7, 4,
                0, 1, 5, 5, 4, 0,
                1, 5, 6, 6, 2, 1
        };
        return cubeIndices;
    }
    public static VBO get_cubeGreenFlatVBO(float _width, float _height, float _depth){
        VBO vbo = new VBO(GLES20.GL_TRIANGLES);
//        float[] rawData = get_cubeGreenArray(_width, _height, _depth);
//        float[] insert = new float[] {
//                -_width, -_height, _depth,
//                _width, -_height, _depth,
//                _width, _height, _depth,
//                -_width, _height, _depth,
//                -_width, -_height, -_depth,
//                _width, -_height, -_depth,
//                _width, _height, -_depth,
//                -_width, _height, -_depth,
//        };
//        rawData = insert_to_data(rawData, insert, 8, 8);

        int[] flags = new int[] {P, P, P, COPY, COPY, COPY, COPY, N, N, N};
        float[] data = convert_ibo_to_triangles(get_cubeGreenNormalsArray(_width, _height, _depth), get_cubeIndexArray(), flags);
//        float[] data = convert_ibo_to_triangles(rawData, get_cubeIndexArray(), flags);
        vbo.setRawData(data, 10, GLES20.GL_STATIC_DRAW);
        vbo.setStride(new int[]{3, 4, 3});
//        vbo.setIndicesCount(36);
//        vbo.setHandles(new int[]{0, 1, 2});
        return vbo;
    }


    public static VBO get_cubePurpleVBO(float _width, float _height, float _depth){
        VBO vbo = new VBO(GLES20.GL_TRIANGLES);
        PrimitiveArray indices = new PrimitiveArray(get_cubeIndexArray());
        vbo.setRawIndexedData(get_cubePurpleArray(_width, _height, _depth), 36, indices, GLES20.GL_UNSIGNED_SHORT, GLES20.GL_STATIC_DRAW);
        vbo.setStride(new int[]{3, 4});
//        vbo.setHandles(new int[]{0, 1});
//        vbo.setIndicesCount(36);
        return vbo;
    }

    public static VBO get_cubeGreenVBO(float _width, float _height, float _depth){
        VBO vbo = new VBO(GLES20.GL_TRIANGLES);
        PrimitiveArray indices = new PrimitiveArray(get_cubeIndexArray());
        vbo.setRawIndexedData(get_cubeGreenArray(_width, _height, _depth), 36, indices, GLES20.GL_UNSIGNED_SHORT, GLES20.GL_STATIC_DRAW);
        vbo.setStride(new int[]{3, 4});
//        vbo.setHandles(new int[]{0, 1});
//        vbo.setIndicesCount(36);
        return vbo;
    }

    public static VBO get_cubeGreenNormalsVBO(float _width, float _height, float _depth){
        VBO vbo = new VBO(GLES20.GL_TRIANGLES);
        PrimitiveArray indices = new PrimitiveArray(get_cubeIndexArray());
        vbo.setRawIndexedData(get_cubeGreenNormalsArray(_width, _height, _depth), 36, indices, GLES20.GL_UNSIGNED_SHORT, GLES20.GL_STATIC_DRAW);
        vbo.setStride(new int[]{3, 4, 3});
//        vbo.setHandles(new int[]{0, 1, 2});
//        vbo.setIndicesCount(36);
        return vbo;
    }


    public static final int P = 1001;
    public static final int COPY = 1002;
    public static final int N = 1003;
    public static final int ZERO = 1004;
//    public enum flag {P, COPY, N, NULL};


    public static float[] convert_ibo_to_triangles(final float[] _data, final short[] _indices, final int[] _flags){
        float[] out;

        int dataLength = _data.length;
        int vertexCount = _indices.length;
        int width = _flags.length;
        int triangleCount = (vertexCount/3);

        int newLength = vertexCount*width;
        out = new float[newLength];

        Log.d(TAG, "dataLength : "+Integer.toString(dataLength) + " | vertexCount : " + Integer.toString(vertexCount) + " | triangleCount : " + Integer.toString(triangleCount) + " | newLength : " + Integer.toString(newLength));

        float currentData;
        int currentFlag, writeTo, readFrom, currentVertex;
        int normals[] = new int[9];
        float positions[] = new float[9];
        int posCount = 0, normalCount = 0;
        Vec3 v1 = new Vec3();
        Vec3 v2 = new Vec3();
        Vec3 n;
        for (int i = 0 ; i < triangleCount ; i++){

            for (int j = 0 ; j < 3 ; j++){
                for (int k = 0 ; k < width ; k++){

                    currentVertex = _indices[(i*3)+j];
                    readFrom = currentVertex*width+k;
                    writeTo = (i*width*3)+(j*width)+k;


                    currentFlag = _flags[k];
                    currentData = _data[readFrom];
                    out[writeTo] = currentData;
//                    Log.d(TAG, "currentFlag : "+Integer.toString(currentFlag)+" | currentData : "+Float.toString(currentData)+" | out[writeTo] : "+Float.toString(out[writeTo])+" | currentVertex : "+Integer.toString(currentVertex) + " | readFrom : " + Integer.toString(readFrom) + " | writeTo : " + Integer.toString(writeTo));
                    switch (currentFlag) {
                        case P : {
                            positions[(j*3)+posCount] = currentData;
                            posCount = (posCount+1)%3;
                            break;

                        }
                        case COPY : {
                            out[writeTo] = currentData;
                            break;
                        }
                        case N : {
                            normals[(j*3)+normalCount] = writeTo;
                            normalCount = (normalCount+1)%3;
                            break;
                        }
                        case ZERO : {
                            out[writeTo] = 0;
                            break;
                        }
                        default : {
                            break;
                        }
                    }
                }
            }

            v1.set(positions[0]-positions[3], positions[1]-positions[4], positions[2]-positions[5]);
            v2.set(positions[0]-positions[6], positions[1]-positions[7], positions[2]-positions[8]);
            n = v1.cross(v2);
//            n = v2.cross(v1);
            n.normalize();
            for (int j = 0 ; j < 3 ; j++){
                out[normals[j*3]] = n.x;
                out[normals[j*3+1]] = n.y;
                out[normals[j*3+2]] = n.z;
            }
        }
//        Log.d(TAG, Arrays.toString(out));
//        Log.d(TAG, Float.toString(out[0])+Float.toString(out[1]));
        return out;
    }

    public static float[] insert_to_data(final float[] _data, final float[] _insert, final int _vertexCount, final int _position){
//        int vertexCount = _data.length/_width;
        int width = _data.length/_vertexCount;
        int insertWidth = _insert.length/_vertexCount;
        int newWidth = width+insertWidth;

        float[] out = new float[newWidth*_vertexCount];
        int writeTo, readFrom, offset = 0, count = 0;
        for (int i = 0 ; i < _vertexCount ; i++){
            for (int j = 0 ; j < width+1 ; j++){
                if (j == _position){
                    for (int k = 0 ; k < insertWidth ; k++){
                        readFrom = i*insertWidth+k;
                        writeTo = i*newWidth+j+k;
                        out[writeTo] = _insert[readFrom];

                    }
                    offset = insertWidth;
                }
                if (j < width) {
                    readFrom = i * width + j;
                    writeTo = i * newWidth + j + offset;
                    out[writeTo] = _data[readFrom];
                }
            }
            offset = 0;
        }

        return out;
    }

}
