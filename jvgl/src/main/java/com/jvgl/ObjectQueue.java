package com.jvgl;

import java.lang.reflect.Constructor;
import java.util.Arrays;

/**
 * Created by Norad on 10/07/2014.
 * // TODO: could report if it is dirty
 */
public class ObjectQueue<T extends QueueObject, E extends QueueObject>{
    private final static String TAG = new String("JVGL.NObjectQueue ");
    private final static int VBCT = 0;

    private static int ms_nextId = 0;
    private int m_id;
    private Object[] m_array;
    private Object[] m_backArray;
    private byte[] m_arrayActive;
    private int m_size;
    private boolean m_dirty;
    private Constructor<? extends T> m_ctor = null;


    public ObjectQueue(Class<? extends T> _impl, int _initialCapacity) {
        m_id = ms_nextId++;
        m_array = new Object[_initialCapacity];
        m_backArray= new Object[_initialCapacity];
        m_arrayActive = new byte[_initialCapacity];
        m_dirty = false;

        try {
            this.m_ctor = _impl.getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public ObjectQueue(Class<? extends T> _impl){
        this(_impl, 10);
    }

    public int size(){return m_size;}
    @SuppressWarnings("unchecked")
    private T m_array(int _index) {return (T) m_array[_index];}
    public synchronized T get(int _index){return m_array(_index);}
    public int get_id(){return m_id;}
    public void deactivate(int _index){
        m_arrayActive[_index] = 0;
        m_dirty = true;
    }

    private void push_back(T _object){
//        int oldSize = m_size;
//        m_size++;
//        Object[] new_array = new Object[m_size];
//        byte[] new_arrayActive = new byte[m_size];
//        for (int i = 0 ; i < oldSize ; i++){
//            new_array[i] = m_array[i];
//            new_arrayActive[i] = m_arrayActive[i];
//        }
//        new_array[oldSize] = _object;
//        new_arrayActive[oldSize] = 1;
//        m_array = new_array;
//        m_arrayActive = new_arrayActive;
        ensure_capacity_internal(m_size+1);
        m_array[m_size] = _object;
//        m_backArray[m_size] = _object;
        m_arrayActive[m_size] = 1;
        m_size++;
    }

    private void ensure_capacity_internal(int _minCapacity){
        grow(_minCapacity);
    }

    private void grow(int _minCapacity) {
        // overflow-conscious code
        int oldCapacity = m_size;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - _minCapacity < 0)
            newCapacity = _minCapacity;
//        if (newCapacity - MAX_ARRAY_SIZE > 0)
//                     newCapacity = hugeCapacity(minCapacity);
        // minCapacity is usually close to size, so this is a win:
        m_array = Arrays.copyOf(m_array, newCapacity);
//        m_backArray = Arrays.copyOf(m_backArray, newCapacity);
        m_arrayActive = Arrays.copyOf(m_arrayActive, newCapacity);
    }

    @SuppressWarnings("unchecked")
    public synchronized T put(E _dependant, boolean _constant){
        int id = _dependant.get_id();
        T object = findById(id);
//        T newObject;

        if (object == null){
            try {
                object = m_ctor.newInstance();
                object.set_queueObject(_dependant);
                object.set_constant(_constant);
                object.set_active(true);
                push_back(object);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            object.update_queueObject(_dependant);
            object.set_active(true);
            object.set_constant(_constant);
//            newObject = object;
        }
        return object;
    }

    public T findById(int _id){
        int currentId;
        T object;

        for (int i = 0 ; i < m_size ; i++){
            object = m_array(i);
            currentId = object.get_id();
            if(_id == currentId){
                return object;
            }
        }
        return null;
    }


    public synchronized void cleanTasks(){
        T object;
        for (int i = 0 ; i < m_size ; i++){
            object = m_array(i);
            if (!object.get_active() && !object.get_constant()){
                m_arrayActive[i] = 0;
                m_dirty = true;
            }
            object.set_active(false);
        }
        if (m_dirty){
            collectGarbage();
            m_dirty = false;
        }
    }

    public void collectGarbage(){
//        Logger.d(1, VBCT, TAG, "Collecting Garbage");
        int newSize = 0;
        for (int i = 0 ; i < m_size ; i++){
            newSize += (int) m_arrayActive[i];
        }

//        Logger.d(0, VBCT, TAG, "Dirty");
        Object[] new_array = new Object[newSize];
        byte[] new_arrayActive = new byte[newSize];

//        m_array = Arrays.copyOf(m_array, newSize);

        if (newSize > 0){
            int it = 0;
            for (int i = 0 ; i < m_size ; i++){
                if (m_arrayActive[i] == 1){
                    new_array[it] = m_array(i);
                    new_arrayActive[it] = m_arrayActive[i];
//                    m_array[it] = m_backArray[i];
                    it++;
                }
            }
        }

//        m_arrayActive = Arrays.copyOf(m_arrayActive, newSize);

//        m_backArray = Arrays.copyOf(m_array, newSize);
//        Arrays.fill(m_arrayActive, (byte) 1);
        m_array = new_array;
        m_arrayActive = new_arrayActive;
        m_size = newSize;
    }

}
