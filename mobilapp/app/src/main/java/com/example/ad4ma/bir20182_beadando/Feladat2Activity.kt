package com.example.ad4ma.bir20182_beadando

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.TextView





class Feladat2Activity : AppCompatActivity() {

    private var mGestureDetector: GestureDetector? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feladat2)

        val customGestureDetector = CustomGestureDetector()

        mGestureDetector = GestureDetector(this, customGestureDetector)

        mGestureDetector!!.setOnDoubleTapListener(customGestureDetector)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mGestureDetector?.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun onResume() {
        super.onResume()
        BluetoothManager.getInstance()?.send(BluetoothMessages.FELADAT2START.ordinal.toString(),true)
    }
    override fun onPause() {
        super.onPause()
        BluetoothManager.getInstance()?.send(BluetoothMessages.FELADAT2END.ordinal.toString(),true)
    }
}
