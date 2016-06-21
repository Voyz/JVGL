package com.jvgl;

import android.content.Context;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import com.jvgl.Matrix4f;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Norad on 20/07/2014.
 */
public class Uti {
    public static double NANO_TO_MILLI = 1.0 / 1000000.0;
    public static double MILLI_TO_NANO = 1000000.0;

    private static Vec3 m_temp_vec3 = new Vec3();
    public static void set_matrix4f(Matrix4f _m,
                                    float _00, float _01, float _02, float _03,
                                    float _10, float _11, float _12, float _13,
                                    float _20, float _21, float _22, float _23,
                                    float _30, float _31, float _32, float _33){
        _m.set(0,0, _00);   _m.set(0,1, _01);   _m.set(0,2, _02);   _m.set(0,3, _03);
        _m.set(1,0, _10);   _m.set(1,1, _11);   _m.set(1,2, _12);   _m.set(1,3, _13);
        _m.set(2,0, _20);   _m.set(2,1, _21);   _m.set(2,2, _22);   _m.set(2,3, _23);
        _m.set(3,0, _30);   _m.set(3,1, _31);   _m.set(3,2, _32);   _m.set(3,3, _33);
    }

    public static float fit01(float _v, float _min, float _max){
        return _v*(_max-_min)+_min;
    }

    public static float fit01(float _v, double _min, double _max){
        return (float)(_v*(_max-_min)+_min);
    }


    public static void arrayListToVec2(ArrayList<Float> _array, Vec2 _v){
        _v.set(_array.get(0), _array.get(1));
    }

    public static void arrayListToVec3(ArrayList<Float> _array, Vec3 _v){
        _v.set(_array.get(0), _array.get(1), _array.get(2));
    }

    public static ArrayList<Float> newArray3f(float _f1, float _f2, float _f3){
        ArrayList<Float> array = new  ArrayList<Float>();
        array.add(_f1);
        array.add(_f2);
        array.add(_f3);
        return array;
    }

    public static ArrayList<Float> vec3ToArrayList(Vec3 _v, ArrayList<Float> _array){
        _array.set(0, _v.x);
        _array.set(1, _v.y);
        _array.set(2, _v.z);
        return _array;
    }

    public static float lerp(float a, float b, float t) {
        return a + t * (b - a);
    }

    public static void vlerp(Vec3 a, Vec3 b, float t, Vec3 result){
        result.set(a);
        m_temp_vec3.set(b);
        m_temp_vec3.ssub(a);
        m_temp_vec3.mmult(t);
        result.aadd(m_temp_vec3);
    }

    public static float clamp(float _value, float _min, float _max){
        return Math.max(_min, Math.min(_value, _max));
    }

    public static void disableEnableControls(boolean enable, ViewGroup vg){
        for (int i = 0; i < vg.getChildCount(); i++){
            View child = vg.getChildAt(i);
            if (child instanceof ViewGroup){
                disableEnableControls(enable, (ViewGroup)child);
            } else {
                child.setEnabled(enable);
            }
        }
    }

    public static void colour_to_vec3(int _colour, Vec3 _writeTo){
        int r = Color.red(_colour);
        int g = Color.green(_colour);
        int b = Color.blue(_colour);
        _writeTo.set(r, g, b);
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

}
