package com.jvgl;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Norad on 29/03/2015.
 */
public class Cascade {
    private final static String TAG = new String("Cascade");
    private final static float ALMOST_ZERO = 0.001f;

    private float m_x;
    private float m_y;
    private float m_z;
    private float m_vx;
    private float m_vy;
    private float m_vz;
    private float m_dx;
    private float m_dy;
    private float m_dz;
    private float m_credit;
    private boolean m_dirty;
    private boolean m_working;
    private boolean m_doResist;
    private boolean m_loose;
    private boolean m_switching;
    private boolean m_navigated;
    private boolean m_snapping;
    private boolean m_blocking;
    private boolean m_blockingAll;
    private int m_newName;

    private float m_resistance;
    private ArrayList<CascadeShelf> m_shelves;
    private CascadeShelf m_now;

    public static CascadeShelf m_null;
    private float payW;
    private float creditResult;

    public Cascade() {
        m_shelves = new ArrayList<CascadeShelf>();
        m_null = new CascadeShelf();
        m_null.name = "null";
        m_x = 0;
        m_y = 0;
        m_z = 0;
        m_vx = 0;
        m_vy = 0;
        m_vz = 0;
        m_dx = 0;
        m_dy = 0;
        m_dz = 0;
        m_credit = 1;
        m_resistance = 0.6f;
        m_dirty = true;
        m_working = true;
        m_doResist = true;
        m_loose = false;
        m_switching = false;
        m_navigated = false;
        m_newName = -1;
        
        m_snapping = false;
        m_blocking = true;
        m_blockingAll = false;
    }

    public void set_vx(float _vel) {
        m_vx = _vel;
        m_dx = _vel;
        m_dirty = true;
    }

    public void set_vy(float _vel) {
        m_vy = _vel;
        m_dy = _vel;
        m_dirty = true;
    }

    public void set_vz(float _vel) {
        m_vz = _vel;
        m_dz = _vel;
        m_dirty = true;
    }

    public void add_vx(float _vel) {
        m_vx += _vel;
        m_dx = _vel;
        m_dirty = true;
    }

    public void add_vy(float _vel) {
        m_vy += _vel;
        m_dy = _vel;
        m_dirty = true;
    }

    public void add_vz(float _vel) {
        m_vz += _vel;
        m_dz = _vel;
        m_dirty = true;
    }

    public float get_x() {
        return m_x;
    }
    public float get_y() {
        return m_y;
    }
    public float get_z() {
        return m_z;
    }
    public float get_vx() {
        return m_vx;
    }
    public float get_vy() {
        return m_vy;
    }
    public float get_vz() {
        return m_vz;
    }
    public float get_dx() {
        return m_dx;
    }
    public float get_dy() {
        return m_dy;
    }
    public float get_dz() {
        return m_dz;
    }
    public float get_credit() {
        return m_credit;
    }

    public List<CascadeShelf> get_shelves(){ return Collections.unmodifiableList(m_shelves);}

    public void set_shelves(ArrayList<CascadeShelf> _set) {
        m_shelves = _set;
    }

    public CascadeShelf add_shelf(CascadeShelf _add) {
        _add.set_blockingAll(m_blockingAll);
        _add.set_resistance(m_resistance);
        m_shelves.add(_add);
        return _add;
    }

    public String get_newName(){
        return Integer.toString(m_newName++);
    }
    public CascadeShelf new_shelf() {
        CascadeShelf shelf = new CascadeShelf();
        shelf.set_all(m_null);
        shelf.name = get_newName();
        this.add_shelf(shelf);
        return shelf;
    }

    public CascadeShelf get_shelf(String _name){
        int length = m_shelves.size();
        for (int i =0 ; i < length ; i++){
            if (m_shelves.get(i).name == _name){
                return m_shelves.get(i);
            }
        }
        return null;
    }


    public void set_now(CascadeShelf _now) {
        m_now = _now;
        m_now.spread_active(true);
    }

    public CascadeShelf get_now() {
        return m_now;
    }

    public void get_pointer(Vec3 _result) {
        _result.set(m_x, m_y, m_z);
    }

    public boolean get_dirty() {
        return m_dirty;
    }
    public boolean get_working() {
        return m_working;
    }
    public void set_navigated(boolean _set) {
        m_navigated = _set;
        if (!_set && m_blocking){
            m_now.reset_blocking();
        }
    }

    public boolean get_navigated() {
        return m_navigated;
    }

    public void set_loose(boolean _set) {m_loose = _set;}
    public boolean get_loose() {return m_loose;}

    public void set_snapping(boolean _set) {m_snapping = _set;}
    public boolean get_snapping() {return m_snapping;}

    public void set_blocking(boolean _set) {m_blocking= _set;}
    public boolean get_blocking() {return m_blocking;}

    public void set_blockingAll(boolean _set) {
        m_blockingAll= _set;
        spread_blockingAll(_set);
    }
    public boolean get_blockingAll() {return m_blockingAll;}



    public void spread_blockingAll(boolean _spread){
        for (int i = 0; i < m_shelves.size(); i++) {
            m_shelves.get(i).set_blockingAll(_spread);
        }
    }

    public void set_resistance(float _set) {
        m_resistance= _set;
        spread_resistance(_set);
    }
    public float get_resistance() {return m_resistance;}

    public void spread_resistance(float _spread){
        for (int i = 0; i < m_shelves.size(); i++) {
            m_shelves.get(i).set_resistance(_spread);
        }
    }

    private boolean try_stopping() {
        if (m_vx == 0 && m_vy == 0 && m_vz == 0) {
            m_dirty = false;
            return true;
        } else {
            return false;
        }
    }

    private float resist(float _v) {
        if (Math.abs(_v) < ALMOST_ZERO) {
            return 0;
        } else {
            return (_v *= 0.6f); // EXPOSE
        }
    }

    private float get_credit(float _weight) {
        creditResult = m_credit;
        m_credit -= _weight;
        if (m_credit < 0) {
            m_credit = 0;
        } else {
            creditResult = _weight;
        }
        return creditResult;
    }

    private void weigh() {
        float dscale = 5; // EXPOSE
        if (m_now.has_front() != 0 && m_now.get_back().isZero()) {
            CascadeShelf front = m_now.get_front();
            if (m_loose) {
                front.set_vel(m_vx);
            } else {
                front.set_vel(m_dx*dscale);
            }
        }
        if (m_now.has_back() != 0 && m_now.get_front().isZero()) {
            CascadeShelf back = m_now.get_back();
            if (m_loose) {
                back.set_vel(-m_vx);
            } else {
                back.set_vel(-m_dx*dscale);
            }
        }
        if (m_now.has_right() != 0 && m_now.get_left().isZero()) {
            CascadeShelf right = m_now.get_right();
            if (m_loose) {
                right.set_vel(m_vy);
            } else {
                right.set_vel(m_dy*dscale);
            }
        }
        if (m_now.has_left() != 0 && m_now.get_right().isZero()) {
            CascadeShelf left = m_now.get_left();
            if (m_loose) {
                left.set_vel(-m_vy);
            } else {
                left.set_vel(-m_dy*dscale);
            }
        }
        if (m_now.has_top() != 0 && m_now.get_bottom().isZero()) {
            CascadeShelf top = m_now.get_top();
            if (m_loose) {
                top.set_vel(m_vz);
            } else {
                top.set_vel(m_dz);
            }
        }
        if (m_now.has_bottom() != 0 && m_now.get_top().isZero()) {
            CascadeShelf bottom = m_now.get_bottom();
            if (m_loose) {
                bottom.set_vel(-m_vz);
            } else {
                bottom.set_vel(-m_dz);
            }
        }
    }

    private void pay_shelf(CascadeShelf _shelf) {
        payW = 0;
        if (_shelf.get_active()) {
            payW = _shelf.update(m_loose);
        } else {
            payW = _shelf.update(true);
        }
        payW = get_credit(payW);
        _shelf.set_weight(payW);
    }

    synchronized private void update_shelves() {
        CascadeShelf newNow = null;
        for (int i = 0; i < m_shelves.size(); i++) {
            CascadeShelf currShelf = m_shelves.get(i);
            if (currShelf != m_now) {
                if (currShelf.get_weight() > 0.5 && !m_switching) { // EXPOSE
                    newNow = currShelf;
                }
                pay_shelf(currShelf);
            }
        }
        m_now.set_weight(m_credit);

        if (newNow != null) {
            navigate(newNow);
        }

    }



    public void navigate(CascadeShelf _newNow){
        m_now.spread_active(false);

        if (m_blocking){
            _newNow.set_blockade(_newNow.get_opposite(m_now), true);
        }

        m_now = _newNow;
        _newNow.spread_active(true);
        m_vx *= 0.5f;
        m_vy *= 0.5f;
        m_vz *= 0.5f;
        m_navigated = true;
        if (m_snapping){
            m_loose = true;
        }
    }

    public void kill_velocities(boolean _x, boolean _y, boolean _z){
        if (_x){m_vx = 0;}
        if (_y){m_vy = 0;}
        if (_z){m_vz = 0;}
    }

    public void try_switching(){
        float switch_threshold = 0.01f; // EXPOSE
        float v = 0;
        boolean killx = false, killy = false, killz=false;
        CascadeShelf switchTo = null;
        if (m_vz > switch_threshold && m_now.has_top() != 0){
            v = m_vz*100;
            switchTo = m_now.get_top();
            killx = true; killy=true; killz=false;
        }
        if (m_vz < -switch_threshold && m_now.has_bottom() != 0){
            if (v < -m_vz*100) {
                v = -m_vz*100;
                switchTo = m_now.get_bottom();
                killx=true; killy=true; killz=false;
            }
        }

        if (m_vx > switch_threshold && m_now.has_front() != 0){
            if (v < m_vx) {
                v = m_vx;
                switchTo = m_now.get_front();
                killx=false; killy=true; killz=true;
            }
        }
        if (m_vx < -switch_threshold && m_now.has_back() != 0){
            if (v < -m_vx) {
                v = -m_vx;
                switchTo = m_now.get_back();
                killx=false; killy=true; killz=true;
            }
        }
        if (m_vy > switch_threshold && m_now.has_right() != 0){
            if (v < m_vy) {
                v = m_vy;
                switchTo = m_now.get_right();
                killx=true; killy=false; killz= true;
            }
        }
        if (m_vy < -switch_threshold && m_now.has_left() != 0){
            if (v < -m_vy) {
                v = -m_vy;
                switchTo = m_now.get_left();
                killx=true; killy=false; killz= true;
            }
        }
        if (switchTo != null){
            navigate(switchTo);
            kill_velocities(killx, killy, killz);
            m_switching = true;

        }
    }

    public void update() {
        m_credit = 1;

        if (m_dirty) {
            if (!(m_snapping && m_navigated)) {
                weigh();
            }
            m_dx = 0; m_dy = 0; m_dz = 0;

            if (!m_switching && m_loose && !m_navigated) {
                try_switching();
            }
            boolean stopped = try_stopping();
            if (!stopped) {
                m_vx = resist(m_vx);
                m_vy = resist(m_vy);
                m_vz = resist(m_vz);
                m_vx = Uti.clamp(m_vx, m_now.has_back(), m_now.has_front());
                m_vy = Uti.clamp(m_vy, m_now.has_left(), m_now.has_right());
                m_vz = Uti.clamp(m_vz, m_now.has_bottom(), m_now.has_top());
            }

//            m_dx = m_x;
//            m_dz = m_z;
//            m_x = Uti.clamp(m_x + m_vx, m_now.has_back(), m_now.has_front());
//            m_y = Uti.clamp(m_y + m_vy, m_now.has_left(), m_now.has_right());
//            m_z = Uti.clamp(m_z + m_vz, m_now.has_bottom(), m_now.has_top());
//            m_dz = m_z - m_dz;
//            m_dx = m_x - m_dx;

            m_working = true;
        }
        if (m_working) {
            update_shelves();
            if (m_credit == 1){
                m_working = false;
            }
            if (m_credit > 0.55){ // EXPOSE
                m_switching = false;
            }
        }
    }

    synchronized public float get_sum() {
        float result = 0;
        for (int i = 0; i < m_shelves.size(); i++) {
            result += m_shelves.get(i).get_weight();
        }
        return result;
    }

//    public void get_pos(Vec3 _result) {
//        Vec3 tempVec = new Vec3();
//        for (int i = 0; i < m_shelves.size(); i++) {
//            m_shelves.get(i).get_weightedPos(tempVec);
//            _result.aadd(tempVec);
//        }
//    }

    public void get_weights(ArrayList<Float> _result){
        if (_result.size() != m_shelves.size()){
            throw new IllegalArgumentException("Provided array must match the number of shelves");
        } else {
            for (int i = 0; i < m_shelves.size(); i++) {
                _result.set(i, m_shelves.get(i).get_weight());
            }
        }
    }
}