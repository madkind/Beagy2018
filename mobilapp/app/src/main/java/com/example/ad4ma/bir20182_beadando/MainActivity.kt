package com.example.ad4ma.bir20182_beadando

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothState
import app.akexorcist.bluetotohspp.library.DeviceList
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.reflect.jvm.*



class MainActivity : AppCompatActivity() {


     var bt: BluetoothSPP? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BluetoothManager.createInstance(this)
        bt = BluetoothManager.getInstance()

        feladat1.setOnClickListener{
            val intent = Intent(this, Feladat1Activity::class.java)
            startActivity(intent)
        }
        feladat2.setOnClickListener{
            val intent = Intent(this, Feladat2Activity::class.java)
            startActivity(intent)
        }
        if(!bt!!.isBluetoothAvailable()) {
            tbox.text = "No bluetooth!"
            return
        }

        if (!bt!!.isBluetoothEnabled()) {
            bt!!.enable();
        }


        bt!!.setBluetoothConnectionListener(object : BluetoothSPP.BluetoothConnectionListener {
            override fun onDeviceConnected(name: String, address: String) {
                tbox.text = "Bluetooth connected!"
            }

            override fun onDeviceDisconnected() {
                tbox.text = "Bluetooth disconnected!"
            }

            override fun onDeviceConnectionFailed() {
                tbox.text = "Bluetooth connection failed!"
            }
        })



        tbox.text = "Bluetooth avaiable!"
        bt!!.setupService();
        bt!!.startService(BluetoothState.DEVICE_OTHER);
        val intent = Intent(applicationContext, DeviceList::class.java)
        startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE)

        bt!!.setOnDataReceivedListener { data, message ->
            tbox.text = message
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bt!!.connect(data)
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt!!.setupService()
                bt!!.startService(BluetoothState.DEVICE_ANDROID)
            } else {
                // Do something if user doesn't choose any device (Pressed back)
            }
        }
    }

    fun sendMessage(view: View){
        tbox.text = "Message sent!"
        val s= Date().time
        bt!!.send(s.toString() + " DATA", true)
    }

}
