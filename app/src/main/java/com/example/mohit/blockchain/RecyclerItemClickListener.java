package com.example.mohit.blockchain;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Arun on 09-11-2016.
 */

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    private OnItemClickListener listener;
    GestureDetector gestureDetector;

    public interface OnItemClickListener{
        public void onItemClick(View view, int position);

        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public RecyclerItemClickListener(Context context, RecyclerView recyclerView, OnItemClickListener listener){
        this.listener=listener;
        gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childView=rv.findChildViewUnder(e.getX(),e.getY());
        if(childView!=null && listener!=null && gestureDetector.onTouchEvent(e)){
            listener.onItemClick(childView,rv.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
