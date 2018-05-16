package com.example.ad4ma.bir20182_beadando

import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import kotlin.reflect.jvm.*



/**
 * Created by ad4ma on 2018. 04. 14..
 */
class CustomGestureDetector : GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private val SWIPE_MIN_DISTANCE = 120
    private val SWIPE_MAX_OFF_PATH = 250
    private val SWIPE_THRESHOLD_VELOCITY = 200

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
       // Log.d("gesture","onSingleTapConfirmed")
        return true
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        BluetoothManager.getInstance()?.send(BluetoothMessages.STOP.ordinal.toString(), true)
        Log.d("gesture","onDoubleTap")
        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
       // Log.d("gesture","onDoubleTapEvent")
        return true
    }

    override fun onDown(e: MotionEvent): Boolean {
      //  Log.d("gesture","onDown")
        return true
    }

    override fun onShowPress(e: MotionEvent) {
      //  Log.d("gesture","onShowPress")
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
     //   Log.d("gesture","onSingleTapUp")
        return true
    }

    override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
      //  Log.d("gesture","onScroll")
        return true
    }

    override fun onLongPress(e: MotionEvent) {
      //  Log.d("gesture","onLongPress")
    }

    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        val LOGTAG = "SWIPE"
        when (getSlope(e1.x, e1.y, e2.x, e2.y)) {
            1 -> {
                Log.d(LOGTAG, "top")
                BluetoothManager.getInstance()?.send(BluetoothMessages.START.ordinal.toString(), true)
                return true
            }
            2 -> {
                BluetoothManager.getInstance()?.send(BluetoothMessages.LEFT.ordinal.toString(), true)
                Log.d(LOGTAG, "left")
                return true
            }
            3 -> {
                BluetoothManager.getInstance()?.send(BluetoothMessages.DOWN.ordinal.toString(), true)
                Log.d(LOGTAG, "down")
                return true
            }
            4 -> {
                BluetoothManager.getInstance()?.send(BluetoothMessages.RIGHT.ordinal.toString(), true)
                Log.d(LOGTAG, "right")
                return true
            }
        }
        return false
    }

    private fun getSlope(x1: Float, y1: Float, x2: Float, y2: Float): Int {
        val angle = Math.toDegrees(Math.atan2((y1 - y2).toDouble(), (x2 - x1).toDouble()))
        if (angle > 45 && angle <= 135)
        // top
            return 1
        if (angle >= 135 && angle < 180 || angle < -135 && angle > -180)
        // left
            return 2
        if (angle < -45 && angle >= -135)
        // down
            return 3
        return if (angle > -45 && angle <= 45) 4 else 0
    }
}