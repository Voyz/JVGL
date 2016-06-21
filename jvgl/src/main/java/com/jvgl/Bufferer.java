package com.jvgl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Norad on 30/06/2014.
 */
public class Bufferer {

    public static Buffer genericBuffer(final PrimitiveArray _data){
        switch(_data.type()){
            case 1 : {
                ByteBuffer buffer = ByteBuffer.allocateDirect(_data.size() * Sizeof.of_byte()).order(ByteOrder.nativeOrder());
                buffer.put(_data.extractByte()).position(0);
                return buffer;
            }
            case 2 : {
                CharBuffer buffer = ByteBuffer.allocateDirect(_data.size() * Sizeof.of_char()).order(ByteOrder.nativeOrder()).asCharBuffer();
                buffer.put(_data.extractChar()).position(0);
                return buffer;
            }
            case 4 : {
                FloatBuffer buffer = ByteBuffer.allocateDirect(_data.size() * Sizeof.of_float()).order(ByteOrder.nativeOrder()).asFloatBuffer();
                buffer.put(_data.extractFloat()).position(0);
                return buffer;
            }
            case 5 : {
                IntBuffer buffer = ByteBuffer.allocateDirect(_data.size() * Sizeof.of_int()).order(ByteOrder.nativeOrder()).asIntBuffer();
                buffer.put(_data.extractInt()).position(0);
                return buffer;
            }
            case 7 : {
                ShortBuffer buffer = ByteBuffer.allocateDirect(_data.size() * Sizeof.of_short()).order(ByteOrder.nativeOrder()).asShortBuffer();
                buffer.put(_data.extractShort()).position(0);
                return buffer;
            }

            default:{
                break;
            }

        }

        return null;
    }

    public static FloatBuffer floatBuffer(final float[] _data){
        FloatBuffer buffer = ByteBuffer.allocateDirect(_data.length * Sizeof.of_float()).order(ByteOrder.nativeOrder()).asFloatBuffer();
        buffer.put(_data).position(0);
        return buffer;
    }

    public static IntBuffer intBuffer(final int[] _data){
        IntBuffer buffer = ByteBuffer.allocateDirect(_data.length * Sizeof.of_int()).order(ByteOrder.nativeOrder()).asIntBuffer();
        buffer.put(_data).position(0);
        return buffer;
    }

    public static ShortBuffer shortBuffer(final short[] _data){
        ShortBuffer buffer = ByteBuffer.allocateDirect(_data.length * Sizeof.of_short()).order(ByteOrder.nativeOrder()).asShortBuffer();
        buffer.put(_data).position(0);
        return buffer;
    }

    public static ByteBuffer byteBuffer(final byte[] _data){
        ByteBuffer buffer = ByteBuffer.allocateDirect(_data.length * Sizeof.of_byte()).order(ByteOrder.nativeOrder());
        buffer.put(_data).position(0);
        return buffer;
    }

    public static CharBuffer charBuffer(final char[] _data){
        CharBuffer buffer = ByteBuffer.allocateDirect(_data.length * Sizeof.of_char()).order(ByteOrder.nativeOrder()).asCharBuffer();
        buffer.put(_data).position(0);
        return buffer;
    }


}
