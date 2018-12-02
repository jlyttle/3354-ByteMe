package com.example.eptay.byteMeCalendar;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class SwipeDetector implements View.OnTouchListener {
    int count;
    public SwipeDetector(int count){
        this.count = count;
    }
    public static enum Action {
        LR, // Left to Right
        RL, // Right to Left
        TB, // Top to bottom
        BT, // Bottom to Top
        None // when no action was detected
    }

    private static final String logTag = "Swipe";
    private static final int MIN_DISTANCE = 100;
    private float downX, downY, upX, upY;
    private Action mSwipeDetected = Action.None;

    public boolean swipeDetected() {
        return mSwipeDetected != Action.None;
    }

    public Action getAction() {
        return mSwipeDetected;
    }

    public int subtractOne(int swipeCount){
        return -1;
    }
    public int addOne(int swipeCount){
        return 1;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                mSwipeDetected = Action.None;
                return false;

            case MotionEvent.ACTION_MOVE:
                upX = event.getX();
                upY = event.getY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;
                //Log.i(logTag,String.valueOf(deltaX));
                //Log.i(logTag,String.valueOf(deltaX));

                if (deltaY>0 && deltaY<10 && deltaX<0 || deltaY==0 && deltaX>-15 && deltaX<0){
                    //Log.i(logTag,"to right");
                    System.out.println("swipe right");
                    count++;

                }if (deltaY>=0 && deltaY<10 && deltaX>0 || deltaY<0 && deltaX>15 && deltaX<40){
               // Log.i(logTag,"to left");
                System.out.println("swipe left");
            }


                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    // left or right
                    if (deltaX < 0) {
                        mSwipeDetected = Action.LR;
                        return false;
                    }
                    if (deltaX > 0) {

                        mSwipeDetected = Action.RL;
                        return false;
                    }
                } else if (Math.abs(deltaY) > MIN_DISTANCE) {


                    if (deltaY < 0) {
                        //Log.i(logTag,"to bottom");
                        mSwipeDetected = Action.TB;
                        return false;
                    }
                    if (deltaY > 0) {
                        //Log.i(logTag,"to up");
                        mSwipeDetected = Action.BT;
                        return false;
                    }
                }
                return true;
        }
        return false;
    }


}