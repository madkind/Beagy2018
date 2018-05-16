package com.example.ad4ma.bir20182_beadando

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.lang.Math.cos
import java.lang.Math.sin
import java.util.*


class CanvasView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {

    val pointsToDraw: LinkedList<DrawablePoint> = LinkedList()

    init {

        this.isFocusable = true
        this.holder.addCallback(this)

    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.BLACK)
    }

     fun addDrawablePoint(point: DrawablePoint)
     {
         pointsToDraw.add(point)
     }

     fun drawRangeCircle(canvas: Canvas) {
         super.draw(canvas)
         canvas.drawColor(Color.WHITE)
         val p = Paint()
         p.setColor(Color.RED)
         p.setStyle(Paint.Style.STROKE);
         p.strokeWidth = 10.toFloat()
         canvas.drawCircle(canvas.width.toFloat() / 2, canvas.height.toFloat() / 2,canvas.width.toFloat()/2, p)
         canvas.drawCircle(canvas.width.toFloat() / 2, canvas.height.toFloat() / 2,10.toFloat(), p)
    }

    fun drawPoint(canvas: Canvas,  angle: Double, distance: Double){

        //A point at angle theta on the circle whose centre is (x0,y0) and whose radius is r is (x0 + r cos theta, y0 + r sin theta). Now choose theta values evenly spaced between 0 and 2pi.

        val p = Paint()
        p.setColor(Color.BLACK)

        val centerX = canvas.width.toFloat() / 2
        val centerY = canvas.height.toFloat() / 2

        val angleShifted = angle - 180
        val radius = canvas.width.toFloat()/2 * distance / 100

        val x1 = radius * Math.cos(Math.toRadians(angleShifted)) + centerX;
        val y1 = radius * Math.sin(Math.toRadians(angleShifted)) + centerY;

        canvas.drawCircle(x1.toFloat(), y1.toFloat(),10.toFloat(), p)
    }
    // Implements method of SurfaceHolder.Callback
    override fun surfaceCreated(holder: SurfaceHolder) {
      drawAll()
    }

    fun drawAll()
    {
        val canvas  = holder.lockCanvas()
        drawRangeCircle(canvas)
        for (i in pointsToDraw){
            drawPoint(canvas,i.angle,i.distance)
        }
        this.holder.unlockCanvasAndPost(canvas);
    }
    // Implements method of SurfaceHolder.Callback
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }
    override fun surfaceDestroyed(p0: SurfaceHolder?) {
    }

}
