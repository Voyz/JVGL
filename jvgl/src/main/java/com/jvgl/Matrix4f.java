
package com.jvgl;

import android.opengl.Matrix;
import android.renderscript.Matrix3f;
import android.util.Log;

import java.lang.Math;

/**
 * Created by Norad on 30/06/2014.
 */
public class Matrix4f {
    private final static String TAG = new String("JVGL.Matrix4f ");

    private static Matrix4f mTemp = new Matrix4f();
    private static Vec3 mTempPos = new Vec3();
    private final float[] mMat = new float[16];
    private static Object mTempLock = new Object();

//    private  float[] mMatTemp = new float[16];
//    private  float[] mMatTemp2 = new float[16];

    public Matrix4f() {
//        mTemp = new Matrix4f(false);
        loadIdentity();
    }
//
//    public Matrix4f(boolean _withTemp){
//        if (_withTemp){
//            mTemp = new Matrix4f(false);
//        }
//        loadIdentity();
//    }

    public Matrix4f(float[] dataArray) {
        System.arraycopy(dataArray, 0, mMat, 0, mMat.length);
    }

    public float[] getArray() {
        return mMat;
    }

    public float get(int x, int y) {
        return mMat[x*4 + y];
    }

    public void get_rightColumn(Vec3 _out) {_out.set(mMat[3],mMat[7], mMat[11]);}

    public void get_bottomRow(Vec3 _out) {_out.set(mMat[12],mMat[13], mMat[14]);}

    public void get_translate(Vec3 _out){get_bottomRow(_out);}


    public void set(int x, int y, float v) {
        mMat[x*4 + y] = v;
    }

    public void loadIdentity() {
        mMat[0] = 1;
        mMat[1] = 0;
        mMat[2] = 0;
        mMat[3] = 0;

        mMat[4] = 0;
        mMat[5] = 1;
        mMat[6] = 0;
        mMat[7] = 0;

        mMat[8] = 0;
        mMat[9] = 0;
        mMat[10] = 1;
        mMat[11] = 0;

        mMat[12] = 0;
        mMat[13] = 0;
        mMat[14] = 0;
        mMat[15] = 1;
    }

    public void load(Matrix4f src) {
        System.arraycopy(src.getArray(), 0, mMat, 0, mMat.length);
    }

    public void load(Matrix3f src) {
        mMat[0] = src.getArray()[0];
        mMat[1] = src.getArray()[1];
        mMat[2] = src.getArray()[2];
        mMat[3] = 0;

        mMat[4] = src.getArray()[3];
        mMat[5] = src.getArray()[4];
        mMat[6] = src.getArray()[5];
        mMat[7] = 0;

        mMat[8] = src.getArray()[6];
        mMat[9] = src.getArray()[7];
        mMat[10] = src.getArray()[8];
        mMat[11] = 0;

        mMat[12] = 0;
        mMat[13] = 0;
        mMat[14] = 0;
        mMat[15] = 1;
    }

    public void loadRotate(float rot, float x, float y, float z) {
        float c, s;
        mMat[3] = 0;
        mMat[7] = 0;
        mMat[11]= 0;
        mMat[12]= 0;
        mMat[13]= 0;
        mMat[14]= 0;
        mMat[15]= 1;
        rot *= (float)(java.lang.Math.PI / 180.0f);
        c = (float)java.lang.Math.cos(rot);
        s = (float)java.lang.Math.sin(rot);

        float len = (float)java.lang.Math.sqrt(x*x + y*y + z*z);
        if (!(len != 1)) {
            float recipLen = 1.f / len;
            x *= recipLen;
            y *= recipLen;
            z *= recipLen;
        }
        float nc = 1.0f - c;
        float xy = x * y;
        float yz = y * z;
        float zx = z * x;
        float xs = x * s;
        float ys = y * s;
        float zs = z * s;
        mMat[ 0] = x*x*nc +  c;
        mMat[ 4] =  xy*nc - zs;
        mMat[ 8] =  zx*nc + ys;
        mMat[ 1] =  xy*nc + zs;
        mMat[ 5] = y*y*nc +  c;
        mMat[ 9] =  yz*nc - xs;
        mMat[ 2] =  zx*nc - ys;
        mMat[ 6] =  yz*nc + xs;
        mMat[10] = z*z*nc +  c;
    }

    public void loadScale(float x, float y, float z) {
        loadIdentity();
        mMat[0] = x;
        mMat[5] = y;
        mMat[10] = z;
    }

    public void loadTranslate(float x, float y, float z) {
//        loadIdentity();
        mMat[12] = x;
        mMat[13] = y;
        mMat[14] = z;
    }

    public void loadTranslate(Vec3 _pos){
        loadTranslate(_pos.x, _pos.y, _pos.z);
    }

    public void loadMultiply(Matrix4f lhs, Matrix4f rhs) {
        for (int i=0 ; i<4 ; i++) {
            float ri0 = 0;
            float ri1 = 0;
            float ri2 = 0;
            float ri3 = 0;
            for (int j=0 ; j<4 ; j++) {
                float rhs_ij = rhs.get(i,j);
                ri0 += lhs.get(j,0) * rhs_ij;
                ri1 += lhs.get(j,1) * rhs_ij;
                ri2 += lhs.get(j,2) * rhs_ij;
                ri3 += lhs.get(j,3) * rhs_ij;
            }
            set(i,0, ri0);
            set(i,1, ri1);
            set(i,2, ri2);
            set(i,3, ri3);
        }
    }

    public void loadOrtho(float l, float r, float b, float t, float n, float f) {
        loadIdentity();
        mMat[0] = 2 / (r - l);
        mMat[5] = 2 / (t - b);
        mMat[10]= -2 / (f - n);
        mMat[12]= -(r + l) / (r - l);
        mMat[13]= -(t + b) / (t - b);
        mMat[14]= -(f + n) / (f - n);
    }

    public void loadOrthoWindow(int w, int h) {
        loadOrtho(0,w, h,0, -1,1);
    }

    public void loadFrustum(float l, float r, float b, float t, float n, float f) {
        loadIdentity();
        mMat[0] = 2 * n / (r - l);
        mMat[5] = 2 * n / (t - b);
        mMat[8] = (r + l) / (r - l);
        mMat[9] = (t + b) / (t - b);
        mMat[10]= -(f + n) / (f - n);
        mMat[11]= -1;
        mMat[14]= -2*f*n / (f - n);
        mMat[15]= 0;
    }

    public void loadPerspective(float fovy, float aspect, float near, float far) {
        float top = near * (float)Math.tan((float) (fovy * Math.PI / 360.0f));
        float bottom = -top;
        float left = bottom * aspect;
        float right = top * aspect;
        loadFrustum(left, right, bottom, top, near, far);
    }

    public void loadProjectionNormalized(int w, int h) {
        // range -1,1 in the narrow axis at z = 0.
        Matrix4f m1 = new Matrix4f();
        Matrix4f m2 = new Matrix4f();

        if(w > h) {
            float aspect = ((float)w) / h;
            m1.loadFrustum(-aspect,aspect,  -1,1,  1,100);
        } else {
            float aspect = ((float)h) / w;
            m1.loadFrustum(-1,1, -aspect,aspect, 1,100);
        }

        m2.loadRotate(180, 0, 1, 0);
        m1.loadMultiply(m1, m2);

        m2.loadScale(-2, 2, 1);
        m1.loadMultiply(m1, m2);

        m2.loadTranslate(0, 0, 2);
        m1.loadMultiply(m1, m2);

        load(m1);
    }

    public synchronized void mmultiply(Matrix4f rhs){
//        mMatTemp2 = rhs.getArray();
//        Matrix.multiplyMM(mMat, 0, mMat, 0, rhs.getArray(), 0);
        Matrix.multiplyMM(mMat, 0, rhs.getArray(), 0, mMat, 0);
//
//        mMatTemp[0] = mMat[0] * mMatTemp2[0] + mMat[1] * mMatTemp2[4] + mMat[2] * mMatTemp2[8] + mMat[3] * mMatTemp2[12];
//        mMatTemp[1] = mMat[0] * mMatTemp2[1] + mMat[1] * mMatTemp2[5] + mMat[2] * mMatTemp2[9] + mMat[3] * mMatTemp2[13];
//        mMatTemp[2] = mMat[0] * mMatTemp2[2] + mMat[1] * mMatTemp2[6] + mMat[2] * mMatTemp2[10] + mMat[3] * mMatTemp2[14];
//        mMatTemp[3] = mMat[0] * mMatTemp2[3] + mMat[1] * mMatTemp2[7] + mMat[2] * mMatTemp2[11] + mMat[3] * mMatTemp2[15];
//        mMatTemp[4] = mMat[4] * mMatTemp2[0] + mMat[5] * mMatTemp2[4] + mMat[6] * mMatTemp2[8] + mMat[7] * mMatTemp2[12];
//        mMatTemp[5] = mMat[4] * mMatTemp2[1] + mMat[5] * mMatTemp2[5] + mMat[6] * mMatTemp2[9] + mMat[7] * mMatTemp2[13];
//        mMatTemp[6] = mMat[4] * mMatTemp2[2] + mMat[5] * mMatTemp2[6] + mMat[6] * mMatTemp2[10] + mMat[7] * mMatTemp2[14];
//        mMatTemp[7] = mMat[4] * mMatTemp2[3] + mMat[5] * mMatTemp2[7] + mMat[6] * mMatTemp2[11] + mMat[7] * mMatTemp2[15];
//        mMatTemp[8] = mMat[8] * mMatTemp2[0] + mMat[9] * mMatTemp2[4] + mMat[10] * mMatTemp2[8] + mMat[11] * mMatTemp2[12];
//        mMatTemp[9] = mMat[8] * mMatTemp2[1] + mMat[9] * mMatTemp2[5] + mMat[10] * mMatTemp2[9] + mMat[11] * mMatTemp2[13];
//        mMatTemp[10] = mMat[8] * mMatTemp2[2] + mMat[9] * mMatTemp2[6] + mMat[10] * mMatTemp2[10] + mMat[11] * mMatTemp2[14];
//        mMatTemp[11] = mMat[8] * mMatTemp2[3] + mMat[9] * mMatTemp2[7] + mMat[10] * mMatTemp2[11] + mMat[11] * mMatTemp2[15];
//        mMatTemp[12] = mMat[12] * mMatTemp2[0] + mMat[13] * mMatTemp2[4] + mMat[14] * mMatTemp2[8] + mMat[15] * mMatTemp2[12];
//        mMatTemp[13] = mMat[12] * mMatTemp2[1] + mMat[13] * mMatTemp2[5] + mMat[14] * mMatTemp2[9] + mMat[15] * mMatTemp2[13];
//        mMatTemp[14] = mMat[12] * mMatTemp2[2] + mMat[13] * mMatTemp2[6] + mMat[14] * mMatTemp2[10] + mMat[15] * mMatTemp2[14];
//        mMatTemp[15] = mMat[12] * mMatTemp2[3] + mMat[13] * mMatTemp2[7] + mMat[14] * mMatTemp2[11] + mMat[15] * mMatTemp2[15];
//        copyMatrixToMatrix(mMatTemp, mMat);
    }
    public void toOrigin(){
        synchronized (mTempLock) {
            get_translate(mTempPos);
            loadTranslate(0, 0, 0);
        }
    }

    public void fromOrigin(){
        synchronized (mTempLock) {
            loadTranslate(mTempPos);
        }
    }

    public  void multiply(Matrix4f rhs) {
        synchronized (mTempLock) {
            mTemp.loadMultiply(this, rhs);
            load(mTemp);
        }
    }
    public  void rotate(float rot, float x, float y, float z) {
        synchronized (mTempLock) {
            mTemp.loadRotate(rot, x, y, z);
            this.mmultiply(mTemp);
        }
    }

    public  void scale(float x, float y, float z) {
//        Matrix4f tmp = new Matrix4f();
        synchronized (mTempLock) {
            mTemp.loadScale(x, y, z);
            this.mmultiply(mTemp);
        }
    }

    public  void translate(float x, float y, float z) {
//        Matrix4f tmp = new Matrix4f();
        synchronized (mTempLock) {
            mTemp.loadTranslate(x, y, z);
            this.multiply(mTemp);
        }
    }
    private float computeCofactor(int i, int j) {
        int c0 = (i+1) % 4;
        int c1 = (i+2) % 4;
        int c2 = (i+3) % 4;
        int r0 = (j+1) % 4;
        int r1 = (j+2) % 4;
        int r2 = (j+3) % 4;

        float minor = (mMat[c0 + 4*r0] * (mMat[c1 + 4*r1] * mMat[c2 + 4*r2] -
                                            mMat[c1 + 4*r2] * mMat[c2 + 4*r1]))
                     - (mMat[c0 + 4*r1] * (mMat[c1 + 4*r0] * mMat[c2 + 4*r2] -
                                            mMat[c1 + 4*r2] * mMat[c2 + 4*r0]))
                     + (mMat[c0 + 4*r2] * (mMat[c1 + 4*r0] * mMat[c2 + 4*r1] -
                                            mMat[c1 + 4*r1] * mMat[c2 + 4*r0]));

        float cofactor = ((i+j) & 1) != 0 ? -minor : minor;
        return cofactor;
    }


    public boolean inverse() {
        synchronized (mTempLock) {
            for (int i = 0; i < 4; ++i) {
                for (int j = 0; j < 4; ++j) {
                    mTemp.mMat[4 * i + j] = computeCofactor(i, j);
                }
            }

            // Dot product of 0th column of source and 0th row of result
            float det = mMat[0] * mTemp.mMat[0] + mMat[4] * mTemp.mMat[1] +
                    mMat[8] * mTemp.mMat[2] + mMat[12] * mTemp.mMat[3];

            if (Math.abs(det) < 1e-6) {
                return false;
            }

            det = 1.0f / det;
            for (int i = 0; i < 16; ++i) {
                mMat[i] = mTemp.mMat[i] * det;
            }

            return true;
        }
    }


    public boolean inverseTranspose() {
        synchronized (mTempLock) {

            for (int i = 0; i < 4; ++i) {
                for (int j = 0; j < 4; ++j) {
                    mTemp.mMat[4 * j + i] = computeCofactor(i, j);
                }
            }

            float det = mMat[0] * mTemp.mMat[0] + mMat[4] * mTemp.mMat[4] +
                    mMat[8] * mTemp.mMat[8] + mMat[12] * mTemp.mMat[12];

            if (Math.abs(det) < 1e-6) {
                return false;
            }

            det = 1.0f / det;
            for (int i = 0; i < 16; ++i) {
                mMat[i] = mTemp.mMat[i] * det;
            }

            return true;
        }
    }


    public void transpose() {
        for(int i = 0; i < 3; ++i) {
            for(int j = i + 1; j < 4; ++j) {
                float temp = mMat[i*4 + j];
                mMat[i*4 + j] = mMat[j*4 + i];
                mMat[j*4 + i] = temp;
            }
        }
    }

    public static void copyMatrixToMatrix(float[] _s, float[] _t){
        int size = _s.length;
        if (size != _t.length){
            Log.d(TAG, "source.length != target.length. Can't");
        }
        for (int i = 0 ; i < size ; i++ ){
            _t[i] = _s[i];
        }
    }



}
