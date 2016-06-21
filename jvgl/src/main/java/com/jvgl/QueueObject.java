package com.jvgl;

/**
 * Created by Norad on 08/08/2014.
 */
public interface QueueObject {
//    public boolean m_active;
//    public boolean m_constant;
//    public int m_id;

    public void update_queueObject(QueueObject _dependant);
    public void set_queueObject(QueueObject _dependant);

    public int get_id();
    public boolean get_constant();
    public boolean get_active();
    public void set_id(int _id);
    public void set_constant(boolean _constant);
    public void set_active(boolean _active);

//    public NQueueObject make_object(){};
//    public NQueueObject[] make_array(int _size){};


}
