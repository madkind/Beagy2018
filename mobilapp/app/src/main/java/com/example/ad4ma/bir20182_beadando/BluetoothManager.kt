package com.example.ad4ma.bir20182_beadando

import android.content.Context
import app.akexorcist.bluetotohspp.library.BluetoothSPP

/**
 * Created by ad4ma on 2018. 04. 17..
 */
class BluetoothManager {

    companion object {

        @Volatile private var INSTANCE: BluetoothSPP? = null


        fun  getInstance(): BluetoothSPP?{
            return INSTANCE;
        }

        fun createInstance(context: Context) {
        INSTANCE = BluetoothSPP(context)
    }

    }
}