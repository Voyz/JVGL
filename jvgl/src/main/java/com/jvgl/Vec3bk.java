package com.jvgl;


public class Vec3bk{
	public float x;
	public float y;
	public float z;

	public Vec3bk(float _x, float _y, float _z){
		x=_x;
		y=_y;
		z=_z;
	}

	public Vec3bk(Vec3bk _v){
		x=_v.x;
		y=_v.y;
		z=_v.z;
	}

	public Vec3bk(int _x){
		x=_x;
		y=_x;
		z=_x;
	}

	public Vec3bk(float _x){
		x=_x;
		y=_x;
		z=_x;
	}

	public void set(Vec3bk _v){
		x = _v.x;
		y = _v.y;
		z = _v.z;
	}

	public void set(float _x, float _y, float _z){
		x = _x;
		y = _y;
		z = _z;
	}

	public void set(float _x){
		x = _x;
		y = _x;
		z = _x;
	}

	public void aadd(Vec3bk _v){

		z += _v.z;
	}
	
	public void aadd(float _x){
		x += _x;
		y += _x;
		z += _x;
	}
	public void ssub(Vec3bk _v){
		x -= _v.x;
		y -= _v.y;
		z -= _v.z;
	}
	
	public void ssub(float _x){
		x -= _x;
		y -= _x;
		z -= _x;
	}
	
	public void mmult(Vec3bk _v){
		x *= _v.x;
		y *= _v.y;
		z *= _v.z;
	}
	
	public void mmult(float _x){
		x *= _x;
		y *= _x;
		z *= _x;
	}
	
	public void ddiv(Vec3bk _v){
		x /= _v.x;
		y /= _v.y;
		z /= _v.z;
	}
	
	public void ddiv(float _x){
		x /= _x;
		y /= _x;
		z /= _x;
	}
	
	public Vec3bk add(Vec3bk _v){
		
		return new Vec3bk(x+_v.x, y+_v.y, z+_v.z);
	}
	
	public Vec3bk add(float _x){
		
		return new Vec3bk(x+_x, y+_x, z+_x);
	}
	
	public Vec3bk sub(Vec3bk _v){
		
		return new Vec3bk(x-_v.x, y-_v.y, z-_v.z);
	}
	
	public Vec3bk sub(float _x){
		
		return new Vec3bk(x-_x, y-_x, z-_x);
	}

	public Vec3bk mult(Vec3bk _v){
		
		return new Vec3bk(x*_v.x, y*_v.y, z*_v.z);
	}
	
	public Vec3bk mult(float _x){
		
		return new Vec3bk(x*_x, y*_x, z*_x);
	}
	
	public Vec3bk div(Vec3bk _v){
		
		return new Vec3bk(x/_v.x, y/_v.y, z/_v.z);
	}
	
	public Vec3bk div(float _x){
		
		return new Vec3bk(x/_x, y/_x, z/_x);
	}
	
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
	
	public float dot(Vec3bk _v){
		return x*_v.x + y*_v.y + z*_v.z;
	}
	
	public Vec3bk cross(Vec3bk _v){
		return new Vec3bk(
              y*_v.z - z*_v.y,
              z*_v.x - x*_v.z,
              x*_v.y - y*_v.x
             );
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

    public boolean equals(Vec3bk _v){
        return (comp(_v.x, x) &&
            comp(_v.y, y) &&
            comp(_v.z, z));
    }

    public boolean notEquals(Vec3bk _v){
        return !(comp(_v.x, x) &&
                comp(_v.y, y) &&
                comp(_v.z, z));
    }

	
	public boolean comp(float _x, float _y){
		return (_x-0.001f) < (_y)  &&  ((_x)+0.001f) > (_y);
	}
	
    public void clone(Vec3bk _v){
        x = _v.x;
        y = _v.y;
        z = _v.z;
    }
}


