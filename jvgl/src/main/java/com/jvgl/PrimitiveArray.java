package com.jvgl;

/**
 * Created by Norad on 29/06/2014.
 */
public class PrimitiveArray {
    private boolean[] booleanArray = null;
    private byte[] byteArray = null;
    private char[] charArray = null;
    private double[] doubleArray = null;
    private float[] floatArray = null;
    private int[] intArray = null;
    private long[] longArray = null;
    private short[] shortArray = null;

    private byte type = -1;

    public PrimitiveArray(boolean[] b){reset() ;this.booleanArray = b; type = 0;}
    public PrimitiveArray(byte[] b){reset() ;this.byteArray = b; type = 1;}
    public PrimitiveArray(char[] c){reset() ;this.charArray = c; type = 2;}
    public PrimitiveArray(double[] d){reset() ;this.doubleArray = d; type = 3;}
    public PrimitiveArray(float[] f){reset() ;this.floatArray = f; type = 4;}
    public PrimitiveArray(int[] i){reset() ;this.intArray = i; type = 5;}
    public PrimitiveArray(long[] l){reset() ;this.longArray = l; type = 6;}
    public PrimitiveArray(short[] s){reset() ;this.shortArray = s; type = 7;}

    public void setArray(boolean[] b){reset() ;this.booleanArray = b;}
    public void setArray(byte[] b){reset() ;this.byteArray = b;}
    public void setArray(char[] c){reset() ;this.charArray = c;}
    public void setArray(double[] d){reset() ;this.doubleArray = d;}
    public void setArray(float[] f){reset() ;this.floatArray = f;}
    public void setArray(int[] i){reset() ;this.intArray = i;}
    public void setArray(long[] l){reset() ;this.longArray = l;}
    public void setArray(short[] s){reset() ;this.shortArray = s;}

    public boolean[] extractBoolean(){return this.booleanArray;}
    public byte[] extractByte(){return this.byteArray;}
    public char[] extractChar(){return this.charArray;}
    public double[] extractDouble(){return this.doubleArray;}
    public float[] extractFloat(){return this.floatArray;}
    public int[] extractInt(){return this.intArray;}
    public long[] extractLong(){return this.longArray;}
    public short[] extractShort(){return this.shortArray;}


    public void reset(){
        type = -1;
        this.booleanArray = null;
        this.byteArray = null;
        this.charArray = null;
        this.doubleArray = null;
        this.floatArray = null;
        this.intArray = null;
        this.longArray = null;
        this.shortArray = null;
    }

    public byte type(){return type;}

    public int size(){
        int size = 0;
        switch(type){
            case -1 : size = 0; break;
            case 0 : size = booleanArray.length; break;
            case 1 : size = byteArray.length; break;
            case 2 : size = charArray.length; break;
            case 3 : size = doubleArray.length; break;
            case 4 : size = floatArray.length; break;
            case 5 : size = intArray.length; break;
            case 6 : size = longArray.length; break;
            case 7 : size = shortArray.length; break;
            default : size = 0 ; break;
        }

        return size;
    }
}
