package com.jvgl;

/**
 * Created by Norad on 06/08/2014.
 */
public class Random {
    private final static java.util.Random m_randomGen = new java.util.Random((long) (System.currentTimeMillis()*0.1234f*12));

    public static void setRandomSeed(long _seed){m_randomGen.setSeed(_seed);}


    public static float randomFloat(double _min, double _max){
        return (float)(m_randomGen.nextFloat()*(_max-_min)+_min);
    }

    public static Integer randomInt(int _min, int _max){
        return (int)(m_randomGen.nextInt(_max-_min)+_min);
    }

    public static Vec2 newRandomVec2(double _min, double _max){
        return new Vec2(randomFloat(_min, _max), randomFloat(_min, _max));
    }
    public static void randomiseVec2(Vec2 _vector, double _min, double _max){
        _vector.set(randomFloat(_min, _max), randomFloat(_min, _max));
    }

    public static Vec3 newRandomVec3(double _min, double _max){
        return new Vec3(randomFloat(_min, _max), randomFloat(_min, _max), randomFloat(_min, _max));
    }
    public static void randomiseVec3(Vec3 _vector, double _min, double _max){
        _vector.set(randomFloat(_min, _max), randomFloat(_min, _max), randomFloat(_min, _max));
    }
}
