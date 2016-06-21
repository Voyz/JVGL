package com.jvgl;

import android.opengl.GLES20;
import android.util.Log;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Norad on 28/06/2014.
 */




public class VBO {
    private final static String TAG = "JVGL.VBO";

    private boolean m_bound = false;
    private boolean m_doStride = false;
    private int m_stride = 0;
    private boolean m_allocated = false;
    private boolean m_indexed = false;
    private int m_indicesCount = 0;
    private int m_indexType;


    private int m_mode = GLES20.GL_TRIANGLES;

    private ArrayList<Integer> m_vbos = new ArrayList<Integer>();
    private ArrayList<Integer> m_vboSizes = new ArrayList<Integer>();
//    private ArrayList<Integer> m_ibos = new ArrayList<Integer>();
    private int m_ibo;
    private int[] m_offsets;
    private int[] m_handles;


    public VBO(final int _mode){
        m_mode = _mode;
    }

    public void setStride(final int[] _offsets){
//        m_offsets = _offsets;
        m_offsets = new int[_offsets.length+1];

        m_offsets[0] = 0;
        m_handles = new int[_offsets.length];

        int stride = 0;
        for (int i = 0 ; i < _offsets.length ; i++){
            m_offsets[i+1] = _offsets[i];
            stride += _offsets[i];
            m_handles[i] = i;
        }
        m_stride = stride * Sizeof.of_float();
        m_doStride = true;

//        Log.d(TAG, Arrays.toString(m_handles));
    }

    public void render(final int _bufferIndex){
        if (m_vbos.size() <= 0){
            System.out.println("WARNING : VBO unallocated, nothing to render");
        }
        if (!m_doStride){
            //TODO: This fucks up if no handles are set (by setStride or setHandles). Should not.
            if (m_handles.length < m_vbos.size()){
                System.out.println("ERROR : Abort rendering : Number of shader handles smaller then number of buffers. Need to be equal or greater");
                return;
            }

            for (int i = 0 ; i < m_vbos.size() ; i++){
                GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, m_vbos.get(i));
                GLES20.glEnableVertexAttribArray(m_handles[i]);
                GLES20.glVertexAttribPointer(m_handles[i], m_vboSizes.get(i), GLES20.GL_FLOAT, false, 0, 0);
            }
        }
        else {
            if (m_handles.length < m_offsets.length-1){
                System.out.println("ERROR : Abort rendering : Number of shader handles smaller then number of offsets. Need to be equal or greater.");
                return;
            }
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, m_vbos.get(_bufferIndex));
//            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 1);

            int offsetSum = 0;
            for (int i = 1 ; i < m_offsets.length ; i++){
                GLES20.glEnableVertexAttribArray(m_handles[i-1]);
                offsetSum += m_offsets[i-1];
                GLES20.glVertexAttribPointer(m_handles[i-1], m_offsets[i], GLES20.GL_FLOAT, false, m_stride, offsetSum*Sizeof.of_float());
            }
        }
        if (m_indexed){
            GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, m_ibo);
            GLES20.glDrawElements(m_mode, m_indicesCount, m_indexType, 0);
            GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
        }
        else {
            GLES20.glDrawArrays(m_mode, 0, m_indicesCount);
        }

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }

    // set non-indexed data for glDrawArrays. Data must be passed as a Buffer. Use setRawData to pass float[] array data
    public void setData(final Buffer _data, final int _size, final int _mode){

        final int vbo[] = new int[1];
        GLES20.glGenBuffers(1, vbo, 0);
        m_vbos.add(vbo[0]);
        m_vboSizes.add(_size);

//        System.out.println("chuj : " + buffer[0] + " : " + m_buffers.get(m_buffers.size()-1).first);
//        System.out.println(_data.capacity() + " : " + _size);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo[0]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, _data.capacity() * Sizeof.of_float(), _data, _mode);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

        m_allocated = true;
    }

    // set indexed data for glDrawElements. Data must be passed as a Buffer. Use setRawIndexedData to pass float[] array data
    public void setIndexedData(final Buffer _data, final int _size, final Buffer _indexData,
                               final int _indexType, final int _mode){

        m_indexType = _indexType;
        int indexSize = 0;
        switch(_indexType){
            case (GLES20.GL_UNSIGNED_SHORT) : indexSize = Sizeof.of_short(); break;
            case (GLES20.GL_UNSIGNED_INT) : indexSize = Sizeof.of_int(); break;
            case (GLES20.GL_UNSIGNED_BYTE) : indexSize = Sizeof.of_byte(); break;
            default : System.out.println("WARNING : Invalid index type - choose either GL_UNSIGNED_SHORT, GL_UNSIGNED_INT or GL_UNSIGNED_BYTE"); break;
        }

//        System.out.println(indexSize +" : "+ _indexType +" : "+ GLES20.GL_SHORT +" : "+ GLES20.GL_INT +" : "+ GLES20.GL_BYTE);

        m_vboSizes.add(_size);

        final int vbo[] = new int[1];
        GLES20.glGenBuffers(1, vbo, 0);
        m_vbos.add(vbo[0]);

        final int ibo[] = new int[1];
        GLES20.glGenBuffers(1, ibo, 0);
        m_ibo = ibo[0];

//        System.out.println("chuj : " + buffer[0] + " : " + m_buffers.get(m_buffers.size()-1).first);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo[0]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, _data.capacity() * Sizeof.of_float(), _data, _mode);

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, ibo[0]);
        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, _indexData.capacity() * indexSize, _indexData, _mode);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);

        m_indicesCount = _indexData.capacity();
        m_indexed = true;
        m_allocated = true;
    }

    // set non-indexed data for glDrawArrays. Data can be passed as a float[]. Size = number of attributes per vertex
    public void setRawData(final float[] _data, final int _size, final int _mode){

        final int vbo[] = new int[1];
        GLES20.glGenBuffers(1, vbo, 0);
        m_vbos.add(vbo[0]);
        m_vboSizes.add(_size);

        FloatBuffer dataBuffer = Bufferer.floatBuffer(_data);

//        System.out.println(_data.length + " : " + _size);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo[0]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, dataBuffer.capacity() * Sizeof.of_float(), dataBuffer, _mode);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        m_indicesCount = _data.length/_size;
        m_allocated = true;
    }

    // set indexed data for glDrawElements. Data can be passed as a float[]. Size = number of attributes per vertex
    public void setRawIndexedData(final float[] _data, final int _size, final PrimitiveArray _indexData,
                               final int _indexType, final int _mode){

        m_indexType = _indexType;
        m_vboSizes.add(_size);

        final int vbo[] = new int[1];
        GLES20.glGenBuffers(1, vbo, 0);
        m_vbos.add(vbo[0]);

        FloatBuffer dataBuffer;
//        dataBuffer = ByteBuffer.allocateDirect(_data.length * Sizeof.of_float()).order(ByteOrder.nativeOrder()).asFloatBuffer();
//        dataBuffer.put(_data).position(0);
//        dataBuffer = Bufferer.floatBuffer(_data);
        dataBuffer = (FloatBuffer) Bufferer.genericBuffer(new PrimitiveArray(_data));

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo[0]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, dataBuffer.capacity() * Sizeof.of_float(), dataBuffer, _mode);


        final int ibo[] = new int[1];
        GLES20.glGenBuffers(1, ibo, 0);
        m_ibo = ibo[0];

        switch(_indexType){
            case (GLES20.GL_UNSIGNED_SHORT) :{
                int indexSize = Sizeof.of_short();
                ShortBuffer indexBuffer = Bufferer.shortBuffer(_indexData.extractShort());
                GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, ibo[0]);
                GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, indexBuffer.capacity() * indexSize, indexBuffer, _mode);
                break;
            }
            case (GLES20.GL_UNSIGNED_INT) : {
                int indexSize = Sizeof.of_int();
                IntBuffer indexBuffer = Bufferer.intBuffer(_indexData.extractInt());
                GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, ibo[0]);
                GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, indexBuffer.capacity() * indexSize, indexBuffer, _mode);
                break;
            }
            case (GLES20.GL_UNSIGNED_BYTE) :{
                int indexSize = Sizeof.of_byte();
                ByteBuffer indexBuffer = Bufferer.byteBuffer(_indexData.extractByte());
                GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, ibo[0]);
                GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, indexBuffer.capacity() * indexSize, indexBuffer, _mode);
                break;
            }
            default : System.out.println("WARNING : Invalid index type - choose either GL_UNSIGNED_SHORT, GL_UNSIGNED_INT or GL_UNSIGNED_BYTE"); break;
        }

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);

        m_indicesCount = _indexData.size();
        m_indexed = true;
        m_allocated = true;
    }

    public void setIndicesCount(final int _indicesCount){
        m_indicesCount = _indicesCount;
    }

    public void setHandles(final int[] _handles){ m_handles = _handles;}
}


