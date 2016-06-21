package com.jvgl;

import android.util.Log;

/**
 * Created by Norad on 29/03/2015.
 */
public class CascadeShelf {
    protected final static String TAG = "Cascade Shelf";
    protected final static float ALMOST_ZERO = 0.001f;

    public String name;
    protected CascadeShelf front;
    protected CascadeShelf back;
    protected CascadeShelf right;
    protected CascadeShelf left;
    protected CascadeShelf top;
    protected CascadeShelf bottom;
    protected CascadeShelf temp;

    protected boolean block_front = false;
    protected boolean block_back = false;
    protected boolean block_right = false;
    protected boolean block_left = false;
    protected boolean block_top = false;
    protected boolean block_bottom = false;

    protected boolean blockingAll = false;
    protected boolean blocking = false;

    protected float weight;
    protected float vel;
    protected float resistance;
    protected boolean active;

    public CascadeShelf(){
        resistance = 0.85f;
        active = false;
        weight = 0;
        vel = 0;
        name = "CascadeShelf";
    }



    public void set_vel(float _set){vel = _set;}
    public float get_vel(){return vel;}
    public void set_resistance(float _set){resistance = _set;}
    public float get_resistance(){return resistance;}

    public void set_blockingAll(boolean _set){blockingAll = _set;}
    public boolean get_blockingAll(){return blockingAll;}

    public void set_blocking(boolean _set){blocking = _set;}
    public boolean get_blocking(){return blocking;}

    public void get_value(){}

    public CascadeShelf set_front(CascadeShelf _set){front = _set; _set.bounce_back(this); return _set; }
    public CascadeShelf set_back(CascadeShelf _set){back  = _set; _set.bounce_front(this);  return _set; }
    public CascadeShelf set_right(CascadeShelf _set){right  = _set; _set.bounce_left(this);  return _set; }
    public CascadeShelf set_left(CascadeShelf _set){left  = _set; _set.bounce_right(this);  return _set; }
    public CascadeShelf set_top(CascadeShelf _set){top = _set; _set.bounce_bottom(this);     return _set; }
    public CascadeShelf set_bottom(CascadeShelf _set){bottom = _set; _set.bounce_top(this);  return _set; }

    public CascadeShelf set_wrap_front(CascadeShelf _set) {front = _set;  _set.bounce_wrap_back(this);   block_front=true;  return _set; }
    public CascadeShelf set_wrap_back(CascadeShelf _set)  {back  = _set;  _set.bounce_wrap_front(this);  block_back=true;   return _set; }
    public CascadeShelf set_wrap_right(CascadeShelf _set) {right  = _set; _set.bounce_wrap_left(this);   block_right=true;  return _set; }
    public CascadeShelf set_wrap_left(CascadeShelf _set)  {left  = _set;  _set.bounce_wrap_right(this);  block_left=true;   return _set; }
    public CascadeShelf set_wrap_top(CascadeShelf _set)   {top = _set;    _set.bounce_wrap_bottom(this); block_top=true;    return _set; }
    public CascadeShelf set_wrap_bottom(CascadeShelf _set){bottom = _set; _set.bounce_wrap_top(this);    block_bottom=true; return _set; }

    public boolean is_wrap(CascadeShelf _shelf){
        if (_shelf == front){
            return block_front;
        }
        if (_shelf == back){
            return block_back;
        }
        if (_shelf == right){
            return block_right;
        }
        if (_shelf == left){
            return block_left;
        }
        if (_shelf == top){
            return block_top;
        }
        if (_shelf == bottom){
            return block_bottom;
        }
        return false;
    }
    public CascadeShelf get_opposite(CascadeShelf _shelf){
        if (_shelf == front){
            return back;
        }
        if (_shelf == back){
            return front;
        }
        if (_shelf == right){
            return left;
        }
        if (_shelf == left){
            return right;
        }
        if (_shelf == top){
            return bottom;
        }
        if (_shelf == bottom){
            return top;
        }
        return Cascade.m_null;
    }

    public void set_blockade(CascadeShelf _shelf, boolean _set){
        if (_shelf == front){
            block_front = _set;
        }
        if (_shelf == back){
            block_back = _set;
        }
        if (_shelf == right){
            block_right = _set;
        }
        if (_shelf == left){
            block_left = _set;
        }
        if (_shelf == top){
            block_top = _set;
        }
        if (_shelf == bottom){
            block_bottom = _set;
        }
    }

    public void reset_blocking(){
        block_front = false;
        block_back = false;
        block_right = false;
        block_left = false;
        block_top = false;
        block_bottom = false;
    }

    public void set_all(CascadeShelf _set){
        front = _set;
        back = _set;
        right = _set;
        left = _set;
        top = _set;
        bottom = _set;
    }

    public void bounce_front(CascadeShelf _bounce){ front = _bounce;}
    public void bounce_back(CascadeShelf _bounce){back  = _bounce;}
    public void bounce_right(CascadeShelf _bounce){right  = _bounce;}
    public void bounce_left(CascadeShelf _bounce){left  = _bounce;}
    public void bounce_top(CascadeShelf _bounce){top = _bounce;}
    public void bounce_bottom(CascadeShelf _bounce){bottom = _bounce;}

    public void bounce_wrap_front(CascadeShelf _bounce) {front = _bounce;   block_front=true;}
    public void bounce_wrap_back(CascadeShelf _bounce)  {back  = _bounce;   block_back=true;}
    public void bounce_wrap_right(CascadeShelf _bounce) {right  = _bounce;  block_right=true;}
    public void bounce_wrap_left(CascadeShelf _bounce)  {left  = _bounce;   block_left=true;}
    public void bounce_wrap_top(CascadeShelf _bounce)   {top = _bounce;     block_top=true;}
    public void bounce_wrap_bottom(CascadeShelf _bounce){bottom = _bounce;  block_bottom=true;}

    public CascadeShelf get_front(){return front;}
    public CascadeShelf get_back(){return back;}
    public CascadeShelf get_right(){return right;}
    public CascadeShelf get_left(){return left;}
    public CascadeShelf get_top(){return top;}
    public CascadeShelf get_bottom(){return bottom;}

    private boolean has_shelf(CascadeShelf _shelf){
        return (_shelf != null) && (_shelf != Cascade.m_null);
    }

    public int has_front()  { if (has_shelf(front ) && !( block_front  && (blocking || blockingAll) )) {return  1;} else {return 0;}}
    public int has_back()   { if (has_shelf(back  ) && !( block_back   && (blocking || blockingAll) )) {return -1;} else {return 0;}}
    public int has_right()  { if (has_shelf(right ) && !( block_right  && (blocking || blockingAll) )) {return  1;} else {return 0;}}
    public int has_left()   { if (has_shelf(left  ) && !( block_left   && (blocking || blockingAll) )) {return -1;} else {return 0;}}
    public int has_top()    { if (has_shelf(top   ) && !( block_top    && (blocking || blockingAll) )) {return  1;} else {return 0;}}
    public int has_bottom() { if (has_shelf(bottom) && !( block_bottom && (blocking || blockingAll) )) {return -1;} else {return 0;}}

    public void set_weight(float _set){weight = _set;}
    public void add_weight(float _add){weight += _add;}
    public float get_weight(){return weight;}


    public boolean isZero(){return weight == 0;}

    public void set_active(boolean _set){active = _set;}
    public boolean get_active(){return active;}

    public float update(boolean _doResist){
        weight = Uti.clamp(weight+vel, 0, 1);
        if (_doResist) {
            weight *= resistance;
        }
        if (weight < ALMOST_ZERO){
            weight = 0;
        }
        vel = 0;
        return weight;
    }

    public void spread_active(boolean _set){
        front.set_active(_set);
        back.set_active(_set);
        right.set_active(_set);
        left.set_active(_set);
        top.set_active(_set);
        bottom.set_active(_set);
    }
}
