package com.jvgl;

import android.animation.Animator;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;

import java.util.ArrayList;

/**
 * Created by norad on 13/06/15.
 */
public class AnimationQueue {
    private final static String TAG = "jvgl.AnimationQueue";

    private View m_view;
    private ArrayList<Animation> m_queue;
    private Animation m_current;
    public AnimationQueue(){
        m_queue = new ArrayList<Animation>();
    }

    public void set_view(View _set){m_view = _set;}

    public void add(Animation _add){
        m_queue.add(_add);
    }

    public void start(){
        if (m_queue.size() < 1){
            Log.w(TAG, "Animation queue empty");
        } else {
            m_current = m_queue.get(1);

            m_view.setAnimation(m_current);
        }
    }
}
