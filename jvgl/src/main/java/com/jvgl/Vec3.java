package com.jvgl;


public class Vec3 extends Vec2 {
	public float z;


    public Vec3 (){
        super();
        z = 0;
    }
	public Vec3(float _x, float _y, float _z){
        super(_x, _y);
		z=_z;
	}
	
	public Vec3(Vec3 _v){
		super(_v);
		z=_v.z;	
	}
	
	public Vec3(int _x){
		super(_x);
		z=_x;
	}

	public Vec3(float _x){
		super(_x);
		z=_x;
	}

	public void set(Vec3 _v){
		x = _v.x;
		y = _v.y;
		z = _v.z;
	}
	
	public void set(float _x, float _y, float _z){
		super.set(_x,_y);
		z = _z;
	}

	public void set(float _x){
		super.set(_x);
		z = _x;
	}
	
	public void aadd(Vec3 _v){
        super.aadd(_v);
		z += _v.z;
	}
	
	public void aadd(float _x){
		super.aadd(_x);
		z += _x;
	}

    public void aadd(float _x, float _y, float _z){
        super.aadd(_x,_y);
        z += _z;
    }
	public void ssub(Vec3 _v){
        super.ssub(_v);
		z -= _v.z;
	}
	
	public void ssub(float _x){
        super.ssub(_x);
		z -= _x;
	}
	
	public void mmult(Vec3 _v){
        super.mmult(_v);
		z *= _v.z;
	}
	
	public void mmult(float _x){
        super.mmult(_x);
		z *= _x;
	}
	
	public void ddiv(Vec3 _v){
        super.ddiv(_v);
		z /= _v.z;
	}
	
	public void ddiv(float _x){
        super.ddiv(_x);
		z /= _x;
	}
	
	public Vec3 add(Vec3 _v){
		return new Vec3(x+_v.x, y+_v.y, z+_v.z);
	}
	
	public Vec3 add(float _x){
		return new Vec3(x+_x, y+_x, z+_x);
	}
	
	public Vec3 sub(Vec3 _v){
		return new Vec3(x-_v.x, y-_v.y, z-_v.z);
	}
	
	public Vec3 sub(float _x){
		return new Vec3(x-_x, y-_x, z-_x);
	}

	public Vec3 mult(Vec3 _v){
		return new Vec3(x*_v.x, y*_v.y, z*_v.z);
	}
	
	public Vec3 mult(float _x){
		return new Vec3(x*_x, y*_x, z*_x);
	}
	
	public Vec3 div(Vec3 _v){
		return new Vec3(x/_v.x, y/_v.y, z/_v.z);
	}
	
	public Vec3 div(float _x){
		return new Vec3(x/_x, y/_x, z/_x);
	}

    public void mult(float _x, Vec3 _out){ _out.set(x*_x, y*_x, z*_x); }


	public float length(){
		return (float)Math.sqrt((x*x)+(y*y)+(z*z));
	}
	
	public float lengthPow(){
		return (x*x)+(y*y)+(z*z);
	}
	
	public void normalize(){
		float len=(float)Math.sqrt(x*x+y*y+z*z);
		assert len != 0;
		x/=len;
		y/=len;
		z/=len;
	}
	
	public float dot(Vec3 _v){
		return x*_v.x + y*_v.y + z*_v.z;
	}
	
	public Vec3 cross(Vec3 _v){
        return new Vec3(
                y*_v.z - z*_v.y,
                z*_v.x - x*_v.z,
                x*_v.y - y*_v.x
        );
    }

    public void ccross(Vec3 _v1, Vec3 _v2){
        x = _v1.y*_v2.z - _v1.z*_v2.y;
        y = _v1.z*_v2.x - _v1.x*_v2.z;
        z = _v1.x*_v2.y - _v1.y*_v2.x;
    }

    public void limit(float _magnitude){
        if (_magnitude < length()){
            set_length(_magnitude);
        }
    }

    public void set_length(float _magnitude){
        normalize();
        x*=_magnitude;
        y*=_magnitude;
        z*=_magnitude;
    }

    public boolean equals(Vec3 _v){
        return (comp(_v.x, x) &&
            comp(_v.y, y) &&
            comp(_v.z, z));
    }

    public boolean notEquals(Vec3 _v){
        return !(comp(_v.x, x) &&
                comp(_v.y, y) &&
                comp(_v.z, z));
    }

	
	public boolean comp(float _x, float _y){
		return (_x-0.001f) < (_y)  &&  ((_x)+0.001f) > (_y);
	}
	

    public Vec3 clone(){
        return new Vec3(x, y, z);
    }

    public Vec2 cloneVec2(){
        return new Vec2(x,y);
    }

    public String toString(){return "["+Float.toString(x)+", "+Float.toString(y)+", "+Float.toString(z)+"]";}
}


