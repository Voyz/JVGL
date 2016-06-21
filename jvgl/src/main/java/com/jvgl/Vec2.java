package com.jvgl;


public class Vec2 implements QueueObject {
	public float x;
	public float y;
    public static int m_nextId = 0;
    public int m_id;
    public boolean m_constant;
    public boolean m_active;

    public Vec2(){
        m_id = m_nextId++;
        x = 0;
        y = 0;
    }

	public Vec2(float _x, float _y){
        m_id = m_nextId++;
		x=_x;
		y=_y;
	}

	public Vec2(Vec2 _v){
        m_id = m_nextId++;
		x=_v.x;
		y=_v.y;
	}

	public Vec2(int _x){
        m_id = m_nextId++;
		x=_x;
		y=_x;
	}

	public Vec2(float _x){
        m_id = m_nextId++;
		x=_x;
		y=_x;
	}
	
	public void set(Vec2 _v){
		x = _v.x;
		y = _v.y;
	}
	
	public void set(float _x, float _y){
		x = _x;
		y = _y;
	}
	
	public void set(float _x){
		x = _x;
		y = _x;
	}
	
	public void aadd(Vec2 _v){
		x += _v.x;
		y += _v.y;
	}
	
	public void aadd(float _x){
		x += _x;
		y += _x;
	}
    public void aadd(float _x, float _y){
        x += _x;
        y += _y;
    }
	public void ssub(Vec2 _v){
		x -= _v.x;
		y -= _v.y;
	}
	
	public void ssub(float _x){
		x -= _x;
		y -= _x;
	}
	
	public void mmult(Vec2 _v){
		x *= _v.x;
		y *= _v.y;
	}
	
	public void mmult(float _x){
		x *= _x;
		y *= _x;
	}
	
	public void ddiv(Vec2 _v){
		x /= _v.x;
		y /= _v.y;
	}
	
	public void ddiv(float _x){
		x /= _x;
		y /= _x;
	}
	
	public Vec2 add(Vec2 _v){
		
		return new Vec2(x+_v.x, y+_v.y);
	}
	
	public Vec2 add(float _x){
		
		return new Vec2(x+_x, y+_x);
	}
	
	public Vec2 sub(Vec2 _v){
		
		return new Vec2(x-_v.x, y-_v.y);
	}
	
	public Vec2 sub(float _x){
		
		return new Vec2(x-_x, y-_x);
	}

	public Vec2 mult(Vec2 _v){
		
		return new Vec2(x*_v.x, y*_v.y);
	}
	
	public Vec2 mult(float _x){
		
		return new Vec2(x*_x, y*_x);
	}
	
	public Vec2 div(Vec2 _v){
		return new Vec2(x/_v.x, y/_v.y);
	}
	
	public Vec2 div(float _x){
		return new Vec2(x/_x, y/_x);
	}
	
	public float length(){
		return (float)Math.sqrt((x*x)+(y*y));
	}
	
	public float lengthPow(){
		return (x*x)+(y*y);
	}
	
	public void normalize(){
		float len=(float)Math.sqrt(x*x+y*y);
		assert len != 0;
		x/=len;
		y/=len;
	}
	
	public float dot(Vec2 _v){
		return x*_v.x + y*_v.y;
	}
	

    public void limit(float _magnitude){
        float length = lengthPow();
        if ((_magnitude*_magnitude) < length){
            length = (float)Math.sqrt(length);
            x /= length;
            y /= length;
            x*=_magnitude;
            y*=_magnitude;
        }
    }

    public void set_length(float _magnitude){
        normalize();
        x*=_magnitude;
        y*=_magnitude;
    }

    public boolean equals(Vec2 _v){
        return (comp(_v.x, x) &&
            comp(_v.y, y));
    }

    public boolean notEquals(Vec2 _v){
        return !(comp(_v.x, x) &&
                comp(_v.y, y));
    }

	
	public boolean comp(float _x, float _y){
		return (_x-0.001f) < (_y)  &&  ((_x)+0.001f) > (_y);
	}
	
    public Vec2 clone(){
        return new Vec2(x, y);
    }

    public Vec3 cloneVec3(){
        return new Vec3(x,y,0);
    }

    public String toString(){return "["+Float.toString(x)+", "+Float.toString(y)+"]";}

//    @Override
    public void set_queueObject(QueueObject _dependant){
        Vec2 v = (Vec2) _dependant;
        x = v.x;
        y = v.y;
        m_id = v.get_id();
    }


//    @Override
    public void update_queueObject(QueueObject _dependant){
        Vec2 v = (Vec2) _dependant;
        x = v.x;
        y = v.y;
    }

    public int get_id(){return m_id;}
    public boolean get_active(){return m_active;}
    public boolean get_constant(){return m_constant;}
    public void set_id(int _id){m_id = _id;}
    public void set_active(boolean _active){m_active = _active;}
    public void set_constant(boolean _constant){m_constant = _constant;}

}


