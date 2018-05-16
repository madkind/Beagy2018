package com.example.ad4ma.bir20182_beadando

import android.graphics.Canvas
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager

class Feladat1Activity : AppCompatActivity() {

        lateinit var cv: CanvasView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cv = CanvasView(this)
        setContentView(cv)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onResume() {
        super.onResume()
        BluetoothManager.getInstance()?.send(BluetoothMessages.FELADAT1START.ordinal.toString(),true)

        BluetoothManager.getInstance()?.setOnDataReceivedListener { data, message ->
           try {
               Log.d("BT",message)
              var splitted = message.split("|")
               cv.addDrawablePoint(DrawablePoint(splitted[1].toDouble(),splitted[0].toDouble()))

               cv.drawAll()
           }catch (e: Exception){}
        }

    }

    override fun onPause() {
        super.onPause()
        BluetoothManager.getInstance()?.send(BluetoothMessages.FELADAT1END.ordinal.toString(),true)
    }
}
