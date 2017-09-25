package com.example.minsulin.carcontroller.Activity

import android.content.Intent
import android.graphics.Rect
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import kotlinx.android.synthetic.main.activity_main.*
import android.view.ViewGroup
import android.widget.ImageView
import com.example.minsulin.carcontroller.Helper.BluetoothHelper
import com.example.minsulin.carcontroller.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnConnect.setOnClickListener {
            val intent = Intent(this, PairedDevicesListActivity::class.java)
            startActivity(intent)
        }

        //btnControl.isEnabled = BluetoothHelper.isConnected

        btnControl.setOnClickListener {
            val intent = Intent(this, ControlActivity::class.java)
            startActivity(intent)
        }

        btnDisable.setOnClickListener {
            BluetoothHelper.disconnect()
        }

    }

    override fun onResume() {
        super.onResume()
        //btnControl.isEnabled = BluetoothHelper.isConnected
    }

}
