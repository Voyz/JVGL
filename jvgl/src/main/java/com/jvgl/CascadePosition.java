package com.jvgl;

/**
 * Created by norad on 18/05/15.
 */
public class CascadePosition {

    private Vec3 pos;
    private CascadeShelf shelf;

    public CascadePosition(){}

    public CascadePosition(CascadeShelf _shelf, Vec3 _pos){
        shelf = _shelf;
        pos = _pos;
    }
    public void set_shelf(CascadeShelf _set){shelf = _set;}
    public CascadeShelf get_shelf(){return shelf;}

    public void set_pos(Vec3 _set){pos = _set;}
    public Vec3 get_pos(){return pos;}
    public void get_weightedPos(Vec3 _out){pos.mult(shelf.get_weight(), _out);}
}
